package videoteca.jdbc;

import java.awt.geom.GeneralPath;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import videoteca.domain.Comentario;
import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Reserva;
import videoteca.domain.Usuario;

public class GestorBD {

	private static final String SQLITE_FILE = "resources/db/videoteca.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;
    
	public GestorBD() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.format("* Error al cargar el driver de la BBDD: %s\n", e.getMessage());
		}
	}
	
	/**
	 * Inicializa la base de datos creando las tablas si no existen
	 */
	public void inicializarTablas() {
		// CREACIÓN DE TABLAS:
		// Usuario
		String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIO (" +
				"NOMBRE TEXT PRIMARY KEY," +
				"EMAIL TEXT NOT NULL UNIQUE, " +
				"EDAD INTEGER NOT NULL, " +
				"FECHA_NACIMIENTO TEXT NOT NULL, " +
				"CONTRASEÑA TEXT NOT NULL, " +
				"SALDO DOUBLE DEFAULT 0.0)";
		
		// Comentario
		String sqlComentario = "CREATE TABLE IF NOT EXISTS COMENTARIO (" +
				"NOMBRE_USUARIO TEXT NOT NULL," +
				"NOMBRE_PELICULA TEXT NOT NULL, " +
				"VALORACION INTEGER NOT NULL, " + 
				"TEXTO TEXT, " + 
				"PRIMARY KEY (NOMBRE_USUARIO, NOMBRE_PELICULA), " +	// Cada usuario solo podra comentar cada pelicula una vez
				"FOREIGN KEY (NOMBRE_USUARIO) REFERENCES USUARIO(NOMBRE), " + 
				"FOREIGN KEY (NOMBRE_PELICULA) REFERENCES PELICULA(TITULO) ) ";
		
		// Edad Recomendada (NUEVA TABLA)
		String sqlEdadRecomendada = "CREATE TABLE IF NOT EXISTS EDAD_RECOMENDADA (" +
				"ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"CODIGO TEXT NOT NULL UNIQUE," +
				"DESCRIPCION TEXT NOT NULL," +
				"EDAD_MINIMA INTEGER NOT NULL)";
		
		// Pelicula (CAMBIADA-EdadRecomendada)
		String sqlPelicula = "CREATE TABLE IF NOT EXISTS PELICULA (" +
				"ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"TITULO TEXT NOT NULL UNIQUE," +
				"ID_DIRECTOR INTEGER NOT NULL," +
				"AÑO INTEGER NOT NULL," +
				"DURACION INTEGER NOT NULL," +
				"ID_EDAD_RECOMENDADA INTEGER NOT NULL," +
				"PUNTUACION DOUBLE NOT NULL," +
				"PRECIO DOUBLE NOT NULL," +
				"FOREIGN KEY (ID_DIRECTOR) REFERENCES DIRECTOR(ID)," +
				"FOREIGN KEY (ID_EDAD_RECOMENDADA) REFERENCES EDAD_RECOMENDADA(ID))";
		// Reserva
	    String sqlReserva = "CREATE TABLE IF NOT EXISTS RESERVA (" +
	            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
	            "NOMBRE_USUARIO TEXT NOT NULL," +
	            "TITULO_PELICULA TEXT NOT NULL," +
	            "FECHA_RESERVA TEXT NOT NULL," +
	            "FECHA_DEVOLUCION TEXT NOT NULL," +
	            "FOREIGN KEY (NOMBRE_USUARIO) REFERENCES USUARIO(NOMBRE)," +
	            "FOREIGN KEY (TITULO_PELICULA) REFERENCES PELICULA(TITULO))";
		
	    // Director (NUEVA)
		String sqlDirector = "CREATE TABLE IF NOT EXISTS DIRECTOR (" +
				"ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"NOMBRE TEXT NOT NULL UNIQUE)";
		
		// Actor (NUEVA)
		String sqlActor = "CREATE TABLE IF NOT EXISTS ACTOR (" +
				"ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"NOMBRE TEXT NOT NULL UNIQUE)";
		
		// Película-Actor (relación N:M,NUEVA)
		String sqlPeliculaActor = "CREATE TABLE IF NOT EXISTS PELICULA_ACTOR (" +
				"ID_PELICULA INTEGER NOT NULL," +
				"ID_ACTOR INTEGER NOT NULL," +
				"PRIMARY KEY (ID_PELICULA, ID_ACTOR)," +
				"FOREIGN KEY (ID_PELICULA) REFERENCES PELICULA(ID)," +
				"FOREIGN KEY (ID_ACTOR) REFERENCES ACTOR(ID))";
		
		// Película-Género (relación N:M,NUEVA)
		String sqlPeliculaGenero = "CREATE TABLE IF NOT EXISTS PELICULA_GENERO (" +
				"ID_PELICULA INTEGER NOT NULL," +
				"GENERO TEXT NOT NULL," +
				"PRIMARY KEY (ID_PELICULA, GENERO)," +
				"FOREIGN KEY (ID_PELICULA) REFERENCES PELICULA(ID))";
		
		try {
	        java.io.File dbDir = new java.io.File("resources/db");
	        if (!dbDir.exists()) {
	            dbDir.mkdirs();
	            System.out.println("Directorio de base de datos creado: resources/db");
	        }

	        //Conectar a la base de datos
	        Connection con = DriverManager.getConnection(CONNECTION_STRING);
	        Statement stmt = con.createStatement();

	        // Crear las tablas en orden (primero las independientes, luego las dependientes)
	        
	        // independientes
	        stmt.execute(sqlUsuario);
	        System.out.println("Tabla USUARIO creada o ya existe");
	        
	        stmt.execute(sqlDirector);
	        System.out.println("Tabla DIRECTOR creada o ya existe");
	        
	        stmt.execute(sqlActor);
	        System.out.println("Tabla ACTOR creada o ya existe");
	        
	        stmt.execute(sqlPeliculaGenero);
	        System.out.println("Tabla PELICULA_GENERO creada o ya existe");
	        
	        stmt.execute(sqlEdadRecomendada);
	        System.out.println("Tabla EDAD_RECOMENDADA creada o ya existe");
	        //Hay que inicializar las edades recomendadas para que pelicula no de un error
	        inicializarEdadesRecomendadas(con);
	        
	        //PELICULA (depende de DIRECTOR y EdadRecomendada)
	        stmt.execute(sqlPelicula);
	        System.out.println("Tabla PELICULA creada o ya existe");
	        
	        // Tablas intermedias (dependen de PELICULA)
	        stmt.execute(sqlPeliculaActor);
	        System.out.println("Tabla PELICULA_ACTOR creada o ya existe");
	        
	        // 4. Otras tablas dependientes
	        stmt.execute(sqlComentario);
	        System.out.println("Tabla COMENTARIO creada o ya existe");
	        
	        stmt.execute(sqlReserva);
	        System.out.println("Tabla RESERVA creada o ya existe");
	        
	        stmt.close();
	        con.close();
	        
		} catch (Exception e) {
			System.err.format("* Error inicializando base de datos: %s\n", e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Funciones de gestion del usuario

	/**
	 * Verifica si un usuario existe en la base de datos y si la contraseña es correcta
	 * @param contraseña Contraseña del usuario
	 * @return Usuario si existe y la contraseña es correcta, null en caso contrario
	 */
	public Usuario verificarUsuario(String nombreUsuario,String contraseña) {
		Usuario usuario = null;
		String sql = "SELECT * FROM USUARIO WHERE NOMBRE = ? AND CONTRASEÑA = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setString(1, nombreUsuario);
			pst.setString(2, contraseña);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String email = rs.getString("EMAIL");
				int edad = rs.getInt("EDAD");
				String fechaStr = rs.getString("FECHA_NACIMIENTO");
				LocalDate fechaNacimiento = LocalDate.parse(fechaStr);
				double saldo = rs.getDouble("SALDO");
				
				usuario = new Usuario(nombre, email, edad, fechaNacimiento, contraseña);
				usuario.aumentarSaldo(saldo); // Actualizar el saldo
				
				System.out.println("Usuario verificado correctamente: " + nombre);
			} else {
				System.out.println("Nombre de usuario o contraseña incorrectos");
			}
			
			rs.close();
		} catch (Exception e) {
			System.err.format("* Error verificando usuario: %s.\n", e.getMessage());
		}
		
		return usuario;
	}
	
	/**
	 * Verifica si existe un usuario con el nombre proporcionado
	 * @param nombreUsuario nombre de usuario a verificar
	 * @return true si existe, false en caso contrario
	 */
	public boolean existeUsuario(String nombreUsuario) {
		boolean existe = false;
		String sql = "SELECT COUNT(*) FROM USUARIO WHERE NOMBRE = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setString(1, nombreUsuario);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next() && rs.getInt(1) > 0) {
				existe = true;
			}
			
			rs.close();
		} catch (Exception e) {
			System.err.format("* Error verificando existencia de usuario: %s.\n", e.getMessage());
		}
		
		return existe;
	}
	
	/**
	 * Registra un nuevo usuario en la base de datos
	 * @param usuario Usuario a registrar
	 * @return true si se registró correctamente, false en caso contrario
	 */
	public boolean registrarUsuario(Usuario usuario) {
		boolean registrado = false;
		
		// Primero verificar si el email ya existe
		if (existeUsuario(usuario.getNombre())) {
			System.out.println("Ya existe un usuario con ese email");
			return false;
		}
		
		String sql = "INSERT INTO USUARIO (NOMBRE, EMAIL, EDAD, FECHA_NACIMIENTO, CONTRASEÑA, SALDO) VALUES (?, ?, ?, ?, ?, ?)";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setString(1, usuario.getNombre());
			pst.setString(2, usuario.getEmail());
			pst.setInt(3, usuario.getEdad());
			pst.setString(4, usuario.getFechaNacimineto().toString());
			pst.setString(5, usuario.getContraseña());
			pst.setDouble(6, usuario.getSaldo());
			
			if (pst.executeUpdate() == 1) {
				System.out.println("Usuario registrado correctamente: " + usuario.getNombre());
				registrado = true;
			} else {
				System.out.println("No se pudo registrar el usuario");
			}
			
		} catch (Exception e) {
			System.err.format("* Error registrando usuario: %s.\n", e.getMessage());
		}
		
		return registrado;
	}
	
	/**
	 * Obtiene un usuario por nombre y contraseña
	 * @param nombre Nombre del usuario
	 * @param contraseña Contraseña del usuario
	 * @return Usuario si existe y la contraseña es correcta, null en caso contrario
	 */
	public Usuario obtenerUsuario(String nombre, String contraseña) {
		Usuario usuario = null;
		String sql = "SELECT * FROM USUARIO WHERE NOMBRE = ? AND CONTRASEÑA = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setString(1, nombre);
			pst.setString(2, contraseña);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				String email = rs.getString("EMAIL");
				int edad = rs.getInt("EDAD");
				String fechaStr = rs.getString("FECHA_NACIMIENTO");
				LocalDate fechaNacimiento = LocalDate.parse(fechaStr);
				double saldo = rs.getDouble("SALDO");
				
				usuario = new Usuario(nombre, email, edad, fechaNacimiento, contraseña);
				usuario.aumentarSaldo(saldo);
				
				System.out.println("Usuario encontrado: " + nombre);
			} else {
				System.out.println("Nombre o contraseña incorrectos");
			}
			
			rs.close();
		} catch (Exception e) {
			System.err.format("* Error obteniendo usuario por nombre: %s.\n", e.getMessage());
		}
		
		return usuario;
	}
	
	/**
	 * Actualiza el saldo de un usuario en la base de datos
	 * @param nombreUsuario nombre del usuario
	 * @param nuevoSaldo Nuevo saldo
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizarSaldo(String nombreUsuario, double nuevoSaldo) {
		boolean actualizado = false;
		
		String sql = "UPDATE USUARIO SET SALDO = ? WHERE NOMBRE = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setDouble(1, nuevoSaldo);
			pst.setString(2, nombreUsuario);
			
			if (pst.executeUpdate() == 1) {
				System.out.println("Saldo actualizado correctamente");
				actualizado = true;
			} else {
				System.out.println("No se pudo actualizar el saldo");
			}
			
		} catch (Exception e) {
			System.err.format("* Error actualizando saldo: %s.\n", e.getMessage());
		}
		
		return actualizado;
	}
	
	/**
	 * Obtiene un usuario solo por su nombre (método auxiliar)
	 * @param nombre Nombre del usuario
	 * @return Usuario si existe, null en caso contrario
	 */
	private Usuario obtenerUsuarioPorNombre(String nombre) {
	    Usuario usuario = null;
	    String sql = "SELECT * FROM USUARIO WHERE NOMBRE = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, nombre);
	        
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            String email = rs.getString("EMAIL");
	            int edad = rs.getInt("EDAD");
	            String fechaStr = rs.getString("FECHA_NACIMIENTO");
	            LocalDate fechaNacimiento = LocalDate.parse(fechaStr);
	            String contraseña = rs.getString("CONTRASEÑA");
	            double saldo = rs.getDouble("SALDO");
	            
	            usuario = new Usuario(nombre, email, edad, fechaNacimiento, contraseña);
	            usuario.aumentarSaldo(saldo);
	        }
	        
	        rs.close();
	    } catch (Exception e) {
	        System.err.format("* Error obteniendo usuario por nombre: %s\n", e.getMessage());
	    }
	    
	    return usuario;
	}

	
	
	// Funciones de comentarios
	
	public boolean registrarComentarios (Comentario coment) {
		boolean registrado = false;
		
		String sql = "INSERT INTO COMENTARIO (NOMBRE_USUARIO, NOMBRE_PELICULA, VALORACION, TEXTO) VALUES (?,?,?,?)";
		
		try (Connection con =  DriverManager.getConnection(CONNECTION_STRING);
			PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, coment.getUser().getNombre());
			stmt.setString(2, coment.getP().getTitulo());
			stmt.setInt(3, coment.getValoracion());
			stmt.setString(4, coment.getTexto());
			
			if (stmt.executeUpdate() == 1) {
				System.out.println("Comentario añadido correctamente a la pelicula " + coment.getP().getTitulo());
				registrado = true;
			} else {
				System.out.println("No se pudo añadir el comentario");
			}
			
		} catch (Exception e) {
			System.err.format("* Error registrando la pelicula: %s.\n", e.getMessage());
		}
		
		return registrado;
	}
	
	/**
	 * Obtiene todos los comentarios de una película
	 * @param pelicula Película de la que se quieren obtener los comentarios
	 * @return Lista de comentarios de la película
	 */
	public ArrayList<Comentario> obtenerComentariosDePelicula(Pelicula pelicula) {
	    ArrayList<Comentario> comentarios = new ArrayList<>();
	    String sql = "SELECT * FROM COMENTARIO WHERE NOMBRE_PELICULA = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, pelicula.getTitulo());
	        
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            String nombreUsuario = rs.getString("NOMBRE_USUARIO");
	            int valoracion = rs.getInt("VALORACION");
	            String texto = rs.getString("TEXTO");
	            
	            // Obtener el usuario completo de la base de datos
	            Usuario usuario = obtenerUsuarioPorNombre(nombreUsuario);
	            
	            if (usuario != null) {
	                Comentario comentario = new Comentario(valoracion, texto, usuario, pelicula);
	                comentarios.add(comentario);
	            }
	        }
	        
	        rs.close();
	        
	        if (comentarios.isEmpty()) {
	            System.out.println("No se encontraron comentarios para la película: " + pelicula.getTitulo());
	        } else {
	            System.out.println("Se encontraron " + comentarios.size() + " comentarios para la película: " + pelicula.getTitulo());
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error obteniendo comentarios de la película: %s\n", e.getMessage());
	    }
	    
	    return comentarios;
	}
	
	
	public Comentario obtenerComentarioDeUser (Usuario user, Pelicula p) {
		
		Comentario comentario = null;
		String sql = "SELECT * FROM COMENTARIO WHERE NOMBRE_USUARIO = ? AND NOMBRE_PELICULA = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setString(1, user.getNombre());
			pst.setString(2, p.getTitulo());
			
			ResultSet resultSet = pst.executeQuery();
			
			if (resultSet.next()) {
				int valoracionCom = resultSet.getInt("VALORACION");
				String textoCom = resultSet.getString("TEXTO");
				
				comentario = new Comentario(valoracionCom, textoCom, user, p);
				
				System.out.println("Comentario de " + user.getNombre() + " de la pelicula " + p.getTitulo());
				
			} else {
				System.out.println("No se ha encontrado ningun comentario de este usuario en esta pelicula");
			}
			
		} catch (Exception e) {
			System.err.println("* Error al obtener el comentario del usuario: %s.\n" + e.getMessage());
		}
		
		return comentario;
	}
	
	
	public int borrarComentario (Usuario user, Pelicula p) {
		int filasBorradas = 0;
		String sql = "DELETE FROM COMENTARIO WHERE NOMBRE_USUARIO = ? AND NOMBRE_PELICULA = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setString(1, user.getNombre());
			pst.setString(2, p.getTitulo());
			
			filasBorradas = pst.executeUpdate();
			
			if (filasBorradas > 0) {	// Borrar si no esta vacia
				System.out.println("Comentario borrado correctamente");
			} else {
				System.out.println("No se ha podido borrar el comentario");
			}
			
		} catch (Exception e) {
			System.err.println("* Error al borrar el comentario del usuario: %s.\n" + e.getMessage());
		}
		
		return filasBorradas;
		
	}
	
	//Funciones de Edad Recomendada
	/**
	 * Inicializa los registros de edades recomendadas en la base de datos
	 * Solo se ejecuta si la tabla está vacía
	 */
	private void inicializarEdadesRecomendadas(Connection con) throws SQLException {
		// Verifica si ya existe
		String sqlCheck = "SELECT COUNT(*) FROM EDAD_RECOMENDADA";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlCheck);
		
		if (rs.next() && rs.getInt(1) > 0) {
			// Ya existe
			rs.close();
			stmt.close();
			System.out.println("Edades recomendadas ya inicializadas");
			return;
		}
		rs.close();
		stmt.close();
		
		// Inserta las edades recomendadas basadas en el enum
		String sqlInsert = "INSERT INTO EDAD_RECOMENDADA (CODIGO, DESCRIPCION, EDAD_MINIMA) VALUES (?, ?, ?)";
		PreparedStatement pst = con.prepareStatement(sqlInsert);
		
		// TP - Todos los Públicos
		pst.setString(1, "TP");
		pst.setString(2, "Todos los Públicos");
		pst.setInt(3, 0);
		pst.executeUpdate();
		
		// PLUS7
		pst.setString(1, "PLUS7");
		pst.setString(2, "Mayores de 7 años");
		pst.setInt(3, 7);
		pst.executeUpdate();
		
		// PLUS13
		pst.setString(1, "PLUS13");
		pst.setString(2, "Mayores de 13 años");
		pst.setInt(3, 13);
		pst.executeUpdate();
		
		// PLUS16
		pst.setString(1, "PLUS16");
		pst.setString(2, "Mayores de 16 años");
		pst.setInt(3, 16);
		pst.executeUpdate();
		
		// PLUS18
		pst.setString(1, "PLUS18");
		pst.setString(2, "Mayores de 18 años");
		pst.setInt(3, 18);
		pst.executeUpdate();
		
		pst.close();
		System.out.println("Edades recomendadas inicializadas correctamente");
	}
	
	// Funciones de peliculas
	
	/**
	 * Verifica si existe una película con el título proporcionado
	 * @param titulo Título de la película a verificar
	 * @return true si existe, false en caso contrario
	 */
	public boolean existePelicula(String titulo) {
	    boolean existe = false;
	    String sql = "SELECT COUNT(*) FROM PELICULA WHERE TITULO = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, titulo);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next() && rs.getInt(1) > 0) {
	            existe = true;
	        }
	        
	        rs.close();
	    } catch (Exception e) {
	        System.err.format("* Error verificando existencia de película: %s.\n", e.getMessage());
	    }
	    
	    return existe;
	}

	/**
	 * Registra una nueva película en la base de datos (ACTUALIZADA CON EDAD_RECOMENDADA)
	 */
	public boolean registrarPelicula(Pelicula pelicula) {
	    boolean registrado = false;
	    
	    if (existePelicula(pelicula.getTitulo())) {
	        System.out.println("Ya existe una película con ese título");
	        return false;
	    }
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
	        con.setAutoCommit(false); // Iniciar transacción
	        
	        try {
	            // Obtener ID del director
	            int idDirector = insertarOObtenerDirector(con, pelicula.getDirector());
	            
	            // Obtener ID de edad recomendada
	            int idEdadRecomendada = obtenerIdEdadRecomendada(con, pelicula.getEdadRecomendada().name());
	            
	            // Insertar película
	            String sqlPelicula = "INSERT INTO PELICULA (TITULO, ID_DIRECTOR, AÑO, DURACION, ID_EDAD_RECOMENDADA, PUNTUACION, PRECIO) " +
	                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement pstPelicula = con.prepareStatement(sqlPelicula, Statement.RETURN_GENERATED_KEYS);
	            pstPelicula.setString(1, pelicula.getTitulo());
	            pstPelicula.setInt(2, idDirector);
	            pstPelicula.setInt(3, pelicula.getAñoLanzamiento());
	            pstPelicula.setInt(4, pelicula.getDuracion());
	            pstPelicula.setInt(5, idEdadRecomendada);
	            pstPelicula.setDouble(6, pelicula.getPuntuacionMedia());
	            pstPelicula.setDouble(7, pelicula.getPrecio());
	            
	            pstPelicula.executeUpdate();
	            ResultSet rs = pstPelicula.getGeneratedKeys();
	            int idPelicula = 0;
	            if (rs.next()) {
	                idPelicula = rs.getInt(1);
	            }
	            rs.close();
	            pstPelicula.close();
	            
	            // Insertar actores
	            for (String nombreActor : pelicula.getReparto()) {
	                int idActor = insertarOObtenerActor(con, nombreActor);
	                insertarPeliculaActor(con, idPelicula, idActor);
	            }
	            
	            // Insertar géneros
	            for (Genero genero : pelicula.getGeneros()) {
	                insertarPeliculaGenero(con, idPelicula, genero.name());
	            }
	            
	            con.commit(); // Confirmar transacción
	            System.out.println("Película registrada correctamente: " + pelicula.getTitulo());
	            registrado = true;
	            
	        } catch (Exception e) {
	            con.rollback(); // Revertir si hay error
	            throw e;
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error registrando película: %s.\n", e.getMessage());
	    }
	    
	    return registrado;
	}

	/**
	 * Obtiene una película por su título (ACTUALIZADA CON EDAD_RECOMENDADA)
	 */
	public Pelicula obtenerPelicula(String titulo) {
	    Pelicula pelicula = null;
	    
	    String sql = "SELECT p.*, d.NOMBRE as DIRECTOR_NOMBRE, e.CODIGO as EDAD_CODIGO " +
	                 "FROM PELICULA p " +
	                 "INNER JOIN DIRECTOR d ON p.ID_DIRECTOR = d.ID " +
	                 "INNER JOIN EDAD_RECOMENDADA e ON p.ID_EDAD_RECOMENDADA = e.ID " +
	                 "WHERE p.TITULO = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, titulo);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            int idPelicula = rs.getInt("ID");
	            String director = rs.getString("DIRECTOR_NOMBRE");
	            int año = rs.getInt("AÑO");
	            int duracion = rs.getInt("DURACION");
	            String edadCodigo = rs.getString("EDAD_CODIGO");
	            EdadRecomendada edadRecomendada = EdadRecomendada.valueOf(edadCodigo);
	            double puntuacion = rs.getDouble("PUNTUACION");
	            double precio = rs.getDouble("PRECIO");
	            
	            // Obtener reparto
	            ArrayList<String> reparto = obtenerRepartoPelicula(con, idPelicula);
	            
	            // Obtener géneros
	            ArrayList<Genero> generos = obtenerGenerosPelicula(con, idPelicula);
	            
	            pelicula = new Pelicula(titulo, director, reparto, generos, año, duracion, edadRecomendada, puntuacion, precio);
	            System.out.println("Película encontrada: " + titulo);
	        } else {
	            System.out.println("No se encontró la película con título: " + titulo);
	        }
	        
	        rs.close();
	    } catch (Exception e) {
	        System.err.format("* Error obteniendo película: %s.\n", e.getMessage());
	    }
	    
	    return pelicula;
	}

	/**
	 * Carga todas las películas de la base de datos (ACTUALIZADA CON EDAD_RECOMENDADA)
	 */
	public ArrayList<Pelicula> cargarPeliculas() {
	    ArrayList<Pelicula> peliculas = new ArrayList<>();
	    
	    String sql = "SELECT p.*, d.NOMBRE as DIRECTOR_NOMBRE, e.CODIGO as EDAD_CODIGO " +
	                 "FROM PELICULA p " +
	                 "INNER JOIN DIRECTOR d ON p.ID_DIRECTOR = d.ID " +
	                 "INNER JOIN EDAD_RECOMENDADA e ON p.ID_EDAD_RECOMENDADA = e.ID";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         Statement stmt = con.createStatement()) {
	        
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while (rs.next()) {
	            int idPelicula = rs.getInt("ID");
	            String titulo = rs.getString("TITULO");
	            String director = rs.getString("DIRECTOR_NOMBRE");
	            int año = rs.getInt("AÑO");
	            int duracion = rs.getInt("DURACION");
	            String edadCodigo = rs.getString("EDAD_CODIGO");
	            EdadRecomendada edadRecomendada = EdadRecomendada.valueOf(edadCodigo);
	            double puntuacion = rs.getDouble("PUNTUACION");
	            double precio = rs.getDouble("PRECIO");
	            
	            // Obtener reparto
	            ArrayList<String> reparto = obtenerRepartoPelicula(con, idPelicula);
	            
	            // Obtener géneros
	            ArrayList<Genero> generos = obtenerGenerosPelicula(con, idPelicula);
	            
	            Pelicula pelicula = new Pelicula(titulo, director, reparto, generos, año, duracion, edadRecomendada, puntuacion, precio);
	            peliculas.add(pelicula);
	        }
	        
	        rs.close();
	        System.out.println("Cargadas " + peliculas.size() + " películas de la base de datos");
	        
	    } catch (Exception e) {
	        System.err.format("* Error cargando películas: %s.\n", e.getMessage());
	    }
	    
	    return peliculas;
	}

	//Funciones para gestionar las nuevas tablas
	
	private int insertarOObtenerDirector(Connection con, String nombre) throws SQLException {
	    // Comprueba si ya existe
	    String sqlSelect = "SELECT ID FROM DIRECTOR WHERE NOMBRE = ?";
	    PreparedStatement pstSelect = con.prepareStatement(sqlSelect);
	    pstSelect.setString(1, nombre);
	    ResultSet rs = pstSelect.executeQuery();
	    
	    if (rs.next()) {
	        int id = rs.getInt("ID");
	        rs.close();
	        pstSelect.close();
	        return id;
	    }
	    rs.close();
	    pstSelect.close();
	    
	    // Si no existe, insertar
	    String sqlInsert = "INSERT INTO DIRECTOR (NOMBRE) VALUES (?)";
	    PreparedStatement pstInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
	    pstInsert.setString(1, nombre);
	    pstInsert.executeUpdate();
	    
	    ResultSet rsKeys = pstInsert.getGeneratedKeys();
	    int id = 0;
	    if (rsKeys.next()) {
	        id = rsKeys.getInt(1);
	    }
	    rsKeys.close();
	    pstInsert.close();
	    
	    return id;
	}

	private int insertarOObtenerActor(Connection con, String nombre) throws SQLException {
	    // Comprueba si ya existe
	    String sqlSelect = "SELECT ID FROM ACTOR WHERE NOMBRE = ?";
	    PreparedStatement pstSelect = con.prepareStatement(sqlSelect);
	    pstSelect.setString(1, nombre);
	    ResultSet rs = pstSelect.executeQuery();
	    
	    if (rs.next()) {
	        int id = rs.getInt("ID");
	        rs.close();
	        pstSelect.close();
	        return id;
	    }
	    rs.close();
	    pstSelect.close();
	    
	    // Si no existe, insertar
	    String sqlInsert = "INSERT INTO ACTOR (NOMBRE) VALUES (?)";
	    PreparedStatement pstInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
	    pstInsert.setString(1, nombre);
	    pstInsert.executeUpdate();
	    
	    ResultSet rsKeys = pstInsert.getGeneratedKeys();
	    int id = 0;
	    if (rsKeys.next()) {
	        id = rsKeys.getInt(1);
	    }
	    rsKeys.close();
	    pstInsert.close();
	    
	    return id;
	}

	/**
	 * Tabla intermedia entre actor y pelicula
	 */
	private void insertarPeliculaActor(Connection con, int idPelicula, int idActor) throws SQLException {
	    String sql = "INSERT INTO PELICULA_ACTOR (ID_PELICULA, ID_ACTOR) VALUES (?, ?)";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, idPelicula);
	    pst.setInt(2, idActor);
	    pst.executeUpdate();
	    pst.close();
	}

	/**
	 * Tabla intermedia entre pelicula y genero
	 */
	private void insertarPeliculaGenero(Connection con, int idPelicula, String genero) throws SQLException {
	    String sql = "INSERT INTO PELICULA_GENERO (ID_PELICULA, GENERO) VALUES (?, ?)";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, idPelicula);
	    pst.setString(2, genero);
	    pst.executeUpdate();
	    pst.close();
	}

	private ArrayList<String> obtenerRepartoPelicula(Connection con, int idPelicula) throws SQLException {
	    ArrayList<String> reparto = new ArrayList<>();
	    String sql = "SELECT a.NOMBRE FROM ACTOR a " +
	                 "INNER JOIN PELICULA_ACTOR pa ON a.ID = pa.ID_ACTOR " +
	                 "WHERE pa.ID_PELICULA = ?";
	    
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, idPelicula);
	    ResultSet rs = pst.executeQuery();
	    
	    while (rs.next()) {
	        reparto.add(rs.getString("NOMBRE"));
	    }
	    
	    rs.close();
	    pst.close();
	    return reparto;
	}

	private ArrayList<Genero> obtenerGenerosPelicula(Connection con, int idPelicula) throws SQLException {
	    ArrayList<Genero> generos = new ArrayList<>();
	    String sql = "SELECT GENERO FROM PELICULA_GENERO WHERE ID_PELICULA = ?";
	    
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, idPelicula);
	    ResultSet rs = pst.executeQuery();
	    
	    while (rs.next()) {
	        generos.add(Genero.valueOf(rs.getString("GENERO")));
	    }
	    
	    rs.close();
	    pst.close();
	    return generos;
	}

	/**
	 * Actualiza la puntuación de una película en la base de datos
	 * @param titulo Título de la película
	 * @param nuevaPuntuacion Nueva puntuación
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizarPuntuacion(String titulo, double nuevaPuntuacion) {
	    boolean actualizado = false;
	    String sql = "UPDATE PELICULA SET PUNTUACION = ? WHERE TITULO = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setDouble(1, nuevaPuntuacion);
	        pst.setString(2, titulo);
	        
	        if (pst.executeUpdate() == 1) {
	            System.out.println("Puntuación actualizada correctamente");
	            actualizado = true;
	        } else {
	            System.out.println("No se pudo actualizar la puntuación");
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error actualizando puntuación: %s.\n", e.getMessage());
	    }
	    
	    return actualizado;
	}
	
	/**
	 * Obtiene el ID de una edad recomendada por su código
	 */
	private int obtenerIdEdadRecomendada(Connection con, String codigo) throws SQLException {
	    String sql = "SELECT ID FROM EDAD_RECOMENDADA WHERE CODIGO = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setString(1, codigo);
	    ResultSet rs = pst.executeQuery();
	    
	    int id = -1;
	    if (rs.next()) {
	        id = rs.getInt("ID");
	    }
	    
	    rs.close();
	    pst.close();
	    
	    if (id == -1) {
	        throw new SQLException("No se encontró la edad recomendada con código: " + codigo);
	    }
	    
	    return id;
	}
	
	//Funciones de Reserva
	/**
	 * Registra una nueva reserva en la base de datos
	 * @param usuario Usuario que realiza la reserva
	 * @param reserva Reserva a registrar
	 * @return true si se registró correctamente, false en caso contrario
	 */
	public boolean registrarReserva(Usuario usuario, Reserva reserva) {
	    boolean registrado = false;
	    String sql = "INSERT INTO RESERVA (NOMBRE_USUARIO, TITULO_PELICULA, FECHA_RESERVA, FECHA_DEVOLUCION) VALUES (?, ?, ?, ?)";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, usuario.getNombre());
	        pst.setString(2, reserva.getPelicula().getTitulo());
	        pst.setString(3, reserva.getFechaReserva().toString());
	        pst.setString(4, reserva.getFechaDevolucion().toString());
	        
	        if (pst.executeUpdate() == 1) {
	            System.out.println("Reserva registrada correctamente para " + usuario.getNombre());
	            registrado = true;
	        } else {
	            System.out.println("No se pudo registrar la reserva");
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error registrando reserva: %s.\n", e.getMessage());
	    }
	    
	    return registrado;
	}

	/**
	 * Obtiene todas las reservas de un usuario
	 * @param usuario Usuario del que se quieren obtener las reservas
	 * @return Lista de reservas del usuario
	 */
	public ArrayList<Reserva> obtenerReservasDeUsuario(Usuario usuario) {
	    ArrayList<Reserva> reservas = new ArrayList<>();
	    String sql = "SELECT * FROM RESERVA WHERE NOMBRE_USUARIO = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, usuario.getNombre());
	        
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            String tituloPelicula = rs.getString("TITULO_PELICULA");
	            String fechaReservaStr = rs.getString("FECHA_RESERVA");
	            String fechaDevolucionStr = rs.getString("FECHA_DEVOLUCION");
	            
	            LocalDate fechaReserva = LocalDate.parse(fechaReservaStr);
	            LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionStr);
	            
	            // Obtener la película completa
	            Pelicula pelicula = obtenerPelicula(tituloPelicula);
	            
	            if (pelicula != null) {
	                Reserva reserva = new Reserva(pelicula, fechaReserva, fechaDevolucion);
	                reservas.add(reserva);
	            }
	        }
	        
	        rs.close();
	        System.out.println("Se encontraron " + reservas.size() + " reservas para el usuario: " + usuario.getNombre());
	        
	    } catch (Exception e) {
	        System.err.format("* Error obteniendo reservas del usuario: %s\n", e.getMessage());
	    }
	    
	    return reservas;
	}
	
	//TODO por si luego queremos hacer algo con las fechas de fin de la reserva
	/**
	 * Actualiza la fecha de devolución de una reserva
	 * @param usuario Usuario de la reserva
	 * @param pelicula Película de la reserva
	 * @param nuevaFechaDevolucion Nueva fecha de devolución
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizarFechaDevolucion(Usuario usuario, Pelicula pelicula, LocalDate nuevaFechaDevolucion) {
	    boolean actualizado = false;
	    String sql = "UPDATE RESERVA SET FECHA_DEVOLUCION = ? WHERE NOMBRE_USUARIO = ? AND TITULO_PELICULA = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, nuevaFechaDevolucion.toString());
	        pst.setString(2, usuario.getNombre());
	        pst.setString(3, pelicula.getTitulo());
	        
	        if (pst.executeUpdate() > 0) {
	            System.out.println("Fecha de devolución actualizada correctamente");
	            actualizado = true;
	        } else {
	            System.out.println("No se pudo actualizar la fecha de devolución");
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error actualizando fecha de devolución: %s.\n", e.getMessage());
	    }
	    
	    return actualizado;
	}

	/**
	 * Elimina una reserva de la base de datos
	 * @param usuario Usuario de la reserva
	 * @param pelicula Película de la reserva
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public boolean eliminarReserva(Usuario usuario, Pelicula pelicula) {
	    boolean eliminado = false;
	    String sql = "DELETE FROM RESERVA WHERE NOMBRE_USUARIO = ? AND TITULO_PELICULA = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, usuario.getNombre());
	        pst.setString(2, pelicula.getTitulo());
	        
	        if (pst.executeUpdate() > 0) {
	            System.out.println("Reserva eliminada correctamente");
	            eliminado = true;
	        } else {
	            System.out.println("No se encontró la reserva para eliminar");
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error eliminando reserva: %s.\n", e.getMessage());
	    }
	    
	    return eliminado;
	}

	/**
	 * Verifica si un usuario tiene una reserva activa de una película
	 * @param usuario Usuario a verificar
	 * @param pelicula Película a verificar
	 * @return true si existe la reserva, false en caso contrario
	 */
	public boolean existeReserva(Usuario usuario, Pelicula pelicula) {
	    boolean existe = false;
	    String sql = "SELECT COUNT(*) FROM RESERVA WHERE NOMBRE_USUARIO = ? AND TITULO_PELICULA = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, usuario.getNombre());
	        pst.setString(2, pelicula.getTitulo());
	        
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next() && rs.getInt(1) > 0) {
	            existe = true;
	        }
	        
	        rs.close();
	    } catch (Exception e) {
	        System.err.format("* Error verificando existencia de reserva: %s.\n", e.getMessage());
	    }
	    
	    return existe;
	}

}
