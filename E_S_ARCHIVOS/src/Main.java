import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // Definimos el nombre del archivo como una constante para no repetirlo
    private static final String archivo = "lista_compras.txt";

    public static void main(String[] args)  {
        System.out.println("--- Ejercicio 1: Escribiendo archivo nuevo ---");
        escribir();

        System.out.println("\n--- Ejercicio 2: Leyendo el archivo ---");
         leer();

        System.out.println("\n--- Ejercicio 3: Añadiendo al archivo ---");
        añadir();

        System.out.println("\n--- Comprobación final: Volvemos a leer ---");
        leer();}

        //EJERCICIO1.ESCRIBIR EN UN ARCHIVO NUEVO
        public static void escribir() {

            try(
                    BufferedWriter bw =new BufferedWriter(new FileWriter(archivo))){
                bw.write("manzana");
                bw.newLine();
                bw.write("pera");
                bw.newLine();
                bw.write("pan");
                bw.newLine();
                System.out.println("Archivo 'lista_compras.txt' creado con éxito");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        public static void leer(){
            try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
                String lineaActual;
                System.out.println("Contenido de 'lista_compras.txt': ");
                while((lineaActual = br.readLine()) != null){
                    System.out.println(lineaActual);

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        public static void añadir(){
        try(BufferedWriter bw =new BufferedWriter(new FileWriter(archivo,true))){
            bw.write("huevos");
            bw.newLine();
            bw.write("caramelos");
            bw.newLine();
            System.out.println("Se añadieron huevos y caramelos al final del archivo");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

    }
