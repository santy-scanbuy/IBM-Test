package programaFichero;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Programa {

	public static void main(String[] args) throws Exception {
		
		@SuppressWarnings("resource")
		Scanner idCliIn = new Scanner(System.in);
		System.out.print("No ha especificado nigún argumento");
		int id_cliente = idCliIn.nextInt();
		
		if(args.length > 0) {
			// creamos conexión con BD y obtenemos los registros, si existen los guarda en el archivo plano 
			// de lo contrario muestra mensaje que no existen registros para ese cliente
			
			// la url de conexión sería la que tengamos en nuestro sistema
			String urlConex = "jdbc:mysql://localhos...";
			
			try {
				Connection conexion = DriverManager.getConnection(urlConex);
				Statement consulta = conexion.createStatement();
				ResultSet registros = consulta.executeQuery("select from proveedores where id_cliente=" + id_cliente);
				
				// recorremos el resultado y crea el fichero plano
				if(registros.next()) {
					crearFichero(registros);
				} else {
					System.out.println("No existen registros para el id_proveedor: " + id_cliente);
				}
				
				conexion.close();
				
			} catch (Exception e) {
				System.out.println("ERROR AL CONECTAR BD");
				throw new Exception("Error al conectar BD ", e);
			}
			
		} else {
			System.out.println("No ha especificado nigún argumento");
		}
	}
	
	private static void crearFichero(ResultSet registros) throws Exception {
		File f;
		FileWriter fw;
		BufferedWriter bw;
		PrintWriter pr;
				
		try {
			f = new File("proveedores.txt");
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			pr = new PrintWriter(bw);
			while(registros.next()) {
				pr.write(registros.getString("id_proveedor") + ", " + registros.getString("nombre") + ", "
						+ registros.getString("fechaAlta") + "," + registros.getString("id_cliente"));
				pr.append("\n");
			}
			bw.close();
			pr.close();
			
		} catch (Exception e) {
			System.out.println("ERROR AL CREAR FICHERO");
			throw new Exception("Error al crear fichero ", e);
		}
		
	}

}
