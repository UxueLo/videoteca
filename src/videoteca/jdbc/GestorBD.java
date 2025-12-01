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
		String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIO (" +
				"NOMBRE TEXT PRIMARY KEY," +
				"EMAIL TEXT NOT NULL UNIQUE, " +
				"EDAD INTEGER NOT NULL, " +
				"FECHA_NACIMIENTO TEXT NOT NULL, " +
				"CONTRASEÑA TEXT NOT NULL, " +
				"SALDO INTEGER DEFAULT 0)";
		
		String sqlComentario = "CREATE TABLE IF NOT EXISTS COMENTARIO (" +
				"NOMBRE_USUARIO TEXT NOT NULL," +
				"NOMBRE_PELICULA TEXT NOT NULL, " +
				"VALORACION INTEGER NOT NULL, " + 
				"TEXTO TEXT, " + 
				"PRIMARY KEY (NOMBRE_USUARIO, NOMBRE_PELICULA), " +	// Cada usuario solo podra comentar cada pelicula una vez
				"FOREIGN KEY (NOMBRE_USUARIO) REFERENCES USUARIO(NOMBRE), " + 
				"FOREIGN KEY (NOMBRE_PELICULA) REFERENCES PELICULA(TITULO) ) ";
		
		String sqlPelicula = "CREATE TABLE IF NOT EXISTS PELICULA (" +
				"TITULO TEXT PRIMARY KEY," +
				"DIRECTOR TEXT NOT NULL, " +
				"REPARTO TEXT NOT NULL," +
				"GENEROS TEXT NOT NULL," +
				"AÑO INTEGER NOT NULL, " + 
				"DURACION INTEGER NOT NULL, " + 
				"EDAD_RECOMENDADA INTEGER NOT NULL," +
				"PUNTUACION DOUBLE NOT NULL, " +
				"PRECIO DOUBLE NOT NULL ) ";	// TODO No se si faltan mas columnas por meter (reparto, generos, edad recomendada, comentarios)
		
		try {
	        java.io.File dbDir = new java.io.File("resources/db");
	        if (!dbDir.exists()) {
	            dbDir.mkdirs();
	            System.out.println("Directorio de base de datos creado: resources/db");
	        }

	        //Conectar a la base de datos
	        Connection con = DriverManager.getConnection(CONNECTION_STRING);
	        Statement stmt = con.createStatement();

	        // Crear las tablas
	        stmt.execute(sqlUsuario);
	        System.out.println("Tabla USUARIO creada o ya existe");
	        
	        stmt.execute(sqlComentario);
	        System.out.println("Tabla COMENTARIO creada o ya existe");
	        
	        stmt.execute(sqlPelicula);
	        System.out.println("Tabla PELICULA creada o ya existe");
	        
		}catch (Exception e) {
			System.err.format("* Error inicializando base de datos: %s\n", e.getMessage());
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
				int saldo = rs.getInt("SALDO");
				
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
			pst.setInt(6, usuario.getSaldo());
			
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
				int saldo = rs.getInt("SALDO");
				
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
	 * Carga todos los usuarios de la base de datos
	 * @return Lista de usuarios
	 */
	public List<Usuario> cargarUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM USUARIO";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement pst = con.createStatement()) {
			
			ResultSet rs = pst.executeQuery(sql);
			
			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String email = rs.getString("EMAIL");
				int edad = rs.getInt("EDAD");
				String fechaStr = rs.getString("FECHA_NACIMIENTO");
				LocalDate fechaNacimiento = LocalDate.parse(fechaStr);
				String contraseña = rs.getString("CONTRASEÑA");
				int saldo = rs.getInt("SALDO");
				
				Usuario usuario = new Usuario(nombre, email, edad, fechaNacimiento, contraseña);
				usuario.aumentarSaldo(saldo);
				
				usuarios.add(usuario);
			}
			
			rs.close();
		} catch (Exception e) {
			System.err.format("* Error cargando usuarios: %s.\n", e.getMessage());
		}
		
		return usuarios;
	}
	
	/**
	 * Actualiza el saldo de un usuario en la base de datos
	 * @param nombreUsuario nombre del usuario
	 * @param nuevoSaldo Nuevo saldo
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizarSaldo(String nombreUsuario, int nuevoSaldo) {
		boolean actualizado = false;
		String sql = "UPDATE USUARIO SET SALDO = ? WHERE NOMBRE = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setInt(1, nuevoSaldo);
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
	 * Actualiza los datos de un usuario en la base de datos
	 * @param usuario Usuario con los datos actualizados
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizarUsuario(Usuario usuario) {
		boolean actualizado = false;
		String sql = "UPDATE USUARIO SET CONTRASEÑA = ?, EDAD = ?, FECHA_NACIMIENTO = ?, EMAIL = ?, SALDO = ? WHERE NOMBRE = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setString(1, usuario.getContraseña());
			pst.setInt(2, usuario.getEdad());
			pst.setString(3, usuario.getFechaNacimineto().toString());
			pst.setString(4, usuario.getEmail());
			pst.setInt(5, usuario.getSaldo());
			pst.setString(6, usuario.getNombre());
			
			if (pst.executeUpdate() == 1) {
				System.out.println("Usuario actualizado correctamente");
				actualizado = true;
			} else {
				System.out.println("No se pudo actualizar el usuario");
			}
			
		} catch (Exception e) {
			System.err.format("* Error actualizando usuario: %s.\n", e.getMessage());
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
	            int saldo = rs.getInt("SALDO");
	            
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
	 * Registra una nueva película en la base de datos
	 * @param pelicula Película a registrar
	 * @return true si se registró correctamente, false en caso contrario
	 */
	public boolean registrarPelicula(Pelicula pelicula) {
	    boolean registrado = false;
	    
	    // Verificar si la película ya existe
	    if (existePelicula(pelicula.getTitulo())) {
	        System.out.println("Ya existe una película con ese título");
	        return false;
	    }
	    
	    String sql = "INSERT INTO PELICULA (TITULO, DIRECTOR, REPARTO, GENEROS, AÑO, DURACION, EDAD_RECOMENDADA, PUNTUACION, PRECIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        // Convertir ArrayList de reparto a String separado por comas
	        String repartoStr = String.join(",", pelicula.getReparto());
	        
	        // Convertir ArrayList de géneros a String separado por comas
	        ArrayList<String> generosStr = new ArrayList<>();
	        for (Genero g : pelicula.getGeneros()) {
	            generosStr.add(g.name()); 
	        }
	        String generos = String.join(",", generosStr);
	        
	        pst.setString(1, pelicula.getTitulo());
	        pst.setString(2, pelicula.getDirector());
	        pst.setString(3, repartoStr);
	        pst.setString(4, generos);
	        pst.setInt(5, pelicula.getAñoLanzamiento());
	        pst.setInt(6, pelicula.getDuracion());
	        pst.setString(7, pelicula.getEdadRecomendada().name()); // Asumiendo que es un enum
	        pst.setDouble(8, pelicula.getPuntuacionMedia());
	        pst.setDouble(9, pelicula.getPrecio());
	        
	        if (pst.executeUpdate() == 1) {
	            System.out.println("Película registrada correctamente: " + pelicula.getTitulo());
	            registrado = true;
	        } else {
	            System.out.println("No se pudo registrar la película");
	        }
	        
	    } catch (Exception e) {
	        System.err.format("* Error registrando película: %s.\n", e.getMessage());
	    }
	    
	    return registrado;
	}

	/**
	 * Obtiene una película por su título
	 * @param titulo Título de la película
	 * @return Película si existe, null en caso contrario
	 */
	public Pelicula obtenerPelicula(String titulo) {
	    Pelicula pelicula = null;
	    String sql = "SELECT * FROM PELICULA WHERE TITULO = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, titulo);
	        
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            String director = rs.getString("DIRECTOR");
	            
	            // Convertir String de reparto a ArrayList
	            String repartoStr = rs.getString("REPARTO");
	            ArrayList<String> reparto = new ArrayList<>();
	            if (repartoStr != null && !repartoStr.isEmpty()) {
	                for (String actor : repartoStr.split(",")) {
	                    reparto.add(actor.trim());
	                }
	            }
	            
	            // Convertir String de géneros a ArrayList
	            String generosStr = rs.getString("GENEROS");
	            ArrayList<Genero> generos = new ArrayList<>();
	            if (generosStr != null && !generosStr.isEmpty()) {
	                for (String genero : generosStr.split(",")) {
	                    generos.add(Genero.valueOf(genero.trim()));
	                }
	            }
	            
	            int año = rs.getInt("AÑO");
	            int duracion = rs.getInt("DURACION");
	            EdadRecomendada edadRecomendada = EdadRecomendada.valueOf(rs.getString("EDAD_RECOMENDADA"));
	            double puntuacion = rs.getDouble("PUNTUACION");
	            double precio = rs.getDouble("PRECIO");
	            
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
	 * Carga todas las películas de la base de datos
	 * @return Lista de películas
	 */
	public List<Pelicula> cargarPeliculas() {
	    List<Pelicula> peliculas = new ArrayList<>();
	    String sql = "SELECT * FROM PELICULA";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         Statement stmt = con.createStatement()) {
	        
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while (rs.next()) {
	            String titulo = rs.getString("TITULO");
	            String director = rs.getString("DIRECTOR");
	            
	            // Convertir String de reparto a ArrayList
	            String repartoStr = rs.getString("REPARTO");
	            ArrayList<String> reparto = new ArrayList<>();
	            if (repartoStr != null && !repartoStr.isEmpty()) {
	                for (String actor : repartoStr.split(",")) {
	                    reparto.add(actor.trim());
	                }
	            }
	            
	            // Convertir String de géneros a ArrayList
	            String generosStr = rs.getString("GENEROS");
	            ArrayList<Genero> generos = new ArrayList<>();
	            if (generosStr != null && !generosStr.isEmpty()) {
	                for (String genero : generosStr.split(",")) {
	                    generos.add(Genero.valueOf(genero.trim()));
	                }
	            }
	            
	            int año = rs.getInt("AÑO");
	            int duracion = rs.getInt("DURACION");
	            EdadRecomendada edadRecomendada = EdadRecomendada.valueOf(rs.getString("EDAD_RECOMENDADA"));
	            double puntuacion = rs.getDouble("PUNTUACION");
	            double precio = rs.getDouble("PRECIO");
	            
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
	

}
