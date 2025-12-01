package videoteca.jdbc;

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
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;

public class GestorBD {

	private static final String SQLITE_FILE = "resources/db/usuario.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;
    
    private static final String SQLTIE_FILE2 = "resources/db/comentario.db";
    private static final String CONNECTION_STRING2 = "jdbc:sqlite:" + SQLTIE_FILE2; 
    
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
				"NOMBRE_PELI TEXT NOT NULL, " +
				"VALORACION INTEGER NOT NULL, " + 
				"TEXTO TEXT, " + 
				"PRIMARY KEY (NOMBRE_USUARIO, NOMBRE_PELI), " +					// Cada usuario solo podra comentar cada pelicula una vez
				"FOREIGN KEY (NOMBRE_USUARIO) REFERENCES USUARIO(NOMBRE), " + 
				"FOREIGN KEY (NOMBRE_PELICULA) REFERENCES PELICULA(NOMBRE) ) ";
		
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
	 * Obtiene un usuario por nombre
	 * @param nombre Nombre del usuario
	 * @return Usuario si existe, null en caso contrario
	 */
	public Usuario obtenerUsuarioPorNombre (String nombre) {
		Usuario usuario = null;
		
		String sql = "SELECT USUARIO WHERE NOMBRE = ?";
		
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
					
					System.out.println("Usuario encontrado: " + nombre);
				} else {
					System.out.println("Nombre incorrecto");
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
	
	
	// Funciones de comentarios
	
	public boolean registrarComentarios (Comentario coment) {
		boolean registrado = false;
		
		String sql = "INSERT INTO COMENTARIO (NOMBRE_USUARIO, NOMBRE_PELI, VALORACION, TEXTO) VALUES (?,?,?,?)";
		
		try (Connection con =  DriverManager.getConnection(CONNECTION_STRING2);
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
	
	
	public Comentario obtenerComentarioDeUser (String NombreUser, String tituloPeli) {
		
		Comentario comentario = null;
		String sql = "SELECT COMENTARIO WHERE NOMBRE_USUARIO = ? AND NOMBRE_PELI = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING2);
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setString(1, NombreUser);
			pst.setString(2, tituloPeli);
			
			ResultSet resultSet = pst.executeQuery();
			
			if (resultSet.next()) {
				int valoracionCom = resultSet.getInt("VALORACION");
				String textoCom = resultSet.getString("TEXTO");
				
				Usuario user = obtenerUsuarioPorNombre(NombreUser);
				Pelicula peli = obtenerPeliculaPorTitulo(tituloPeli);
				
				comentario = new Comentario(valoracionCom, textoCom, user, peli);
				
				System.out.println("Comentario de " + NombreUser + " de la pelicula " + tituloPeli);
				
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
		String sql = "DELETE FROM COMENTARIO WHERE NOMBRE_USUARIO = ? AND NOMBRE_PELI = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING2);
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
	
	
	
	
	
	//Otras funciones
	
	public Pelicula obtenerPeliculaPorTitulo(String titulo) {
		Pelicula pelicula = null;
		
		// TODO si existe devolver la pelicula buscando por el titulo, igual que el obtenerUsuarioPorNombre (mas arriba)
		
		return pelicula;
	}

}
