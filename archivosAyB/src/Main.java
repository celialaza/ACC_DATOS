import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("1. Copiando contenido de Archivo A...");
        leerA();

        System.out.println("2. Añadiendo contenido de Archivo B...");
        leerB();

        System.out.println("\n¡Proceso completado!");
        System.out.println("Revisa 'archivo_nuevo.txt'");



    }
        public static void leerA(){
            try(BufferedWriter bw = new BufferedWriter(new FileWriter("archivo_nuevo.txt"))) {

            try(BufferedReader br = new BufferedReader(new FileReader("archivo_A.txt"))){
                String lineaActual;
                System.out.println("Contenido de archivo A: ");
                while((lineaActual = br.readLine()) != null) {

                    bw.write(lineaActual);
                    bw.newLine();

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }public static void leerB(){
            try( BufferedWriter bw = new BufferedWriter(new FileWriter("archivo_nuevo.txt",true))) {
        try(BufferedReader br = new BufferedReader(new FileReader("archivo_B.txt"))){
            String lineaActual;
            System.out.println("Contenido de archivo A: ");
            while((lineaActual = br.readLine()) != null) {

                    bw.write(lineaActual);
                    bw.newLine();

                }
            }} catch (IOException e) {
            throw new RuntimeException(e);
        }
            }


}
