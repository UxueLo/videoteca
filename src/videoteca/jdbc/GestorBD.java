package videoteca.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import videoteca.domain.Usuario;

public class GestorBD {

	private static final String SQLITE_FILE = "resources/db/usuario.db";
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
		     Statement stmt = con.createStatement()) {
			
			ResultSet rs = stmt.executeQuery(sql);
			
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
	
	//Otras funciones

}
