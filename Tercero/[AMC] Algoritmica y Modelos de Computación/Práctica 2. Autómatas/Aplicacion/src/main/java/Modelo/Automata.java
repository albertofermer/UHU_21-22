package Modelo;

import Vista.Mensaje;
import java.util.ArrayList;
import java.io.*;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public class Automata implements Cloneable, Proceso {

    private ArrayList<Integer> estadosFinales; //Indica los estados finales
    private ArrayList<Transicion> transiciones;
    private ArrayList<TransicionL> transicionesL;
    private ArrayList<Integer> estados;
    private Integer inicial = 0;
    private static Mensaje vMensaje = new Mensaje();

    /**
     * Constructor de la clase. Inicializa las variables que se usan como
     * ArrayList.
     */
    public Automata() {

        estadosFinales = new ArrayList();
        transiciones = new ArrayList();
        transicionesL = new ArrayList();
        estados = new ArrayList();
    }

    /**
     * Añade una transición a la lista de transiciones del autómata.
     *
     * @param e1 Estado de origen.
     * @param simbolo Símbolo que produce la transición.
     * @param e2 Estados de destino.
     */
    public void agregarTransicion(int e1, String simbolo, ArrayList<Integer> e2) {
        int i = 0;
        boolean encontrado = false;
        //Busca si hay alguna macroTransicion con el estado de origen e1.
        while (!encontrado && i < transiciones.size()) {
            if (transiciones.get(i).getEstadoOrigen() == e1 && transiciones.get(i).getSimbolo().equals(simbolo)) {
                encontrado = true;
                transiciones.get(i).addEstadoDestino(e2);
            } else {
                i++;
            }
        }
        //Si "e1" no está registrado aun en el conjunto de transiciones lo insertamos.
        if (!encontrado) {
            if (estados.contains(e1) && estados.containsAll(e2)) {
                transiciones.add(new Transicion(e1, simbolo, e2));
            }
        }
    }

    /**
     * Obtiene todos los estados del automata
     *
     * @return
     */
    public ArrayList<Integer> getEstados() {
        return estados;
    }

    /**
     * Elimina una transición normal del autómata.
     * @param e1
     * @param simbolo
     * @param e2
     */
    public void borrarTransicion(int e1, String simbolo, ArrayList<Integer> e2) {
        for (int i = 0; i < transiciones.size(); i++) {
            if (transiciones.get(i).getEstadoOrigen() == e1 && transiciones.get(i).getSimbolo() == simbolo && transiciones.get(i).getEstadosDestino().containsAll(e2)) {
                transiciones.remove(i);
            }
        }
    }

    /**
     * Añade una "&lambda;"-transicion a la lista de &lambda;-transiciones del
     * autómata.
     *
     * En el caso de que ya exista una &lambda;-transición con el mismo estado
     * de origen, se añade solamente el estado destino.
     *
     * @param e1 estado origen
     * @param e2 lista de estados destino
     */
    public void agregarL_Transicion(int e1, ArrayList<Integer> e2) {
        int i = 0;
        boolean encontrado = false;
        //Busca si hay alguna macroTransicion con el estado de origen e1.
        while (!encontrado && i < transicionesL.size()) {
            if (transicionesL.get(i).getEstadoOrigen() == e1) {
                encontrado = true;
                transicionesL.get(i).addEstadosDestino(e2);
            } else {
                i++;
            }
        }
        //Si no hay ninguna L_transicion que tenga e1 "e1" como estado de origen, se 
        //añade a la lista.
        if (!encontrado) {
            if (estados.contains(e1) && estados.containsAll(e2)) {
                transicionesL.add(new TransicionL(e1, e2));
            }
        }

    }

    /**
     * Dado un estado de origen y un símbolo, devuelve la lista de estados
     * destino de la transición.
     *
     * Si no existe la transición devuelve una lista con -1 como contenido.
     *
     * @param estadoOrigen Estado de origen.
     * @param simbolo Símbolo que lee de la cadena de caracteres
     * @return entero que representa el estado destino de la transición.
     */
    private ArrayList<Integer> transicion(int estadoOrigen, String simbolo) {
        ArrayList<Integer> estadosDestino = new ArrayList(); //-1 si no existe la macroTransicion
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < transiciones.size()) {
            //Si encuentra una transición con eOrigen y símbolo pasados por
            //parámetro, entonces los estados de destino serán los que devuelva
            //esa transición.
            if (transiciones.get(i).getEstadoOrigen() == estadoOrigen
                    && transiciones.get(i).getSimbolo().equals(simbolo)) {
                encontrado = true;
                estadosDestino.clear();
                estadosDestino.addAll(transiciones.get(i).getEstadosDestino());
            } else {
                i++;
            }
        }
        //Si no lo encuentra significa que no existe ninguna transicion con ese
        //estado de origen por lo que devuelve el destino -1.
        if (!encontrado) {
            estadosDestino.clear();
            estadosDestino.add(-1);
        }

        return estadosDestino;
    }

    /**
     * Realiza una transicion desde el conjunto de estados origen mediante el
     * símbolo pasado por parámetro. Devuelve un conjunto de estados a los que
     * transiciona el autómata con dicho símbolo.
     *
     * @param macroEstado conjunto de estados origen de la transicion
     * @param simbolo símbolo símbolo de la transición
     * @return Lista de estados destino a los que transiciona el autómata.
     */
    private ArrayList<Integer> macroTransicion(ArrayList<Integer> macroEstado, String simbolo) {
        ArrayList<Integer> macroDestino = new ArrayList();
        for (int i = 0; i < macroEstado.size(); i++) {

            //Si la macroTransicion no es al estado muerto (-1), añade todas las transiciones al macroDestino
            if (!transicion(macroEstado.get(i), simbolo).contains(-1)) {
                macroDestino.addAll(transicion(macroEstado.get(i), simbolo));
            }
        }
        return macroDestino;
    }

    /**
     * Indica si el estado pasado por parámetro es un estado final.
     *
     * @param estado
     * @return
     */
    @Override
    public boolean esFinal(int estado) {
        return estadosFinales.contains(estado);
    }

    /**
     * Indica si alguno de los estados pasados por parámetro es un estado final.
     *
     * @param macroEstado lista de estados en los que termina un autómata.
     * @return
     * <p>
     * true - Si alguno pertenece a los estados finales.</p>
     * <p>
     * false - Si ninguno pertenece a los estados finales.</p>
     */
    private boolean esFinal(ArrayList<Integer> macroEstado) {
        //hace la intersección con los estados finales.
        macroEstado.retainAll(getEstadosFinales());
        //se queda solamente con los elementos que tengan en común, por lo que
        //si macroestado no está vacío quiere decir que hay al menos un estado que
        //también se encuentra en los estados finales.
        return !macroEstado.isEmpty();

    }

    /**
     * Obtiene los estados finales del autómata.
     *
     * @return
     */
    public ArrayList<Integer> getEstadosFinales() {
        return estadosFinales;
    }

    /**
     * Devuelve los estados generados mediante la &lambda;-Clausura del estado
     * pasado por parámetro.
     *
     * @param estado Estado al que se le hace la &lambda;-Clausura.
     * @param visitados Estados que ha visitado el algoritmo. Se usa para evitar
     * bucles infinitos.
     * @return
     */
    public ArrayList<Integer> lambdaClausura(int estado, ArrayList<Integer> visitados) {
        ArrayList<Integer> resultados = new ArrayList();
        resultados.add(estado);
        if (!visitados.contains(estado)) {
            for (int i = 0; i < transicionesL.size(); i++) {
                if (transicionesL.get(i).getEstadoOrigen() == estado && !visitados.contains(estado)) {
                    visitados.add(estado);
                    for (int j = 0; j < transicionesL.get(i).getEstadoDestino().size(); j++) {
                        if (!visitados.contains(transicionesL.get(i).getEstadoDestino().get(j))) {
                            resultados.addAll(lambdaClausura(transicionesL.get(i).getEstadoDestino().get(j), visitados));

                        }
                    }
                }
            }
            //Si no existe ninguna transicion Lambda con origen en "estado",
            if (!visitados.contains(estado)) {
                visitados.add(estado);
            }
        } else {
            resultados.clear();
        }
        return resultados;
    }

    /**
     * Lee la cadena pasada por parámetro y determina si es una cadena válida o
     * no.
     *
     * @param cadena
     * @return
     * <p>
     * true - El autómata acaba en un estado final.</p>
     * <p>
     * false - El autómata no acaba en un estado final.</p>
     */
    @Override
    public boolean reconocer(String cadena) {

        String[] simbolo = cadena.split(",");
        ArrayList<Integer> estado = new ArrayList();
        ArrayList<Integer> estadoaux = new ArrayList();
        ArrayList<Integer> visitados = new ArrayList();
        estado.addAll(lambdaClausura(inicial, visitados));
        System.out.println("Inicio: " + estado);
        for (int i = 0; i < simbolo.length; i++) {

            System.out.print("Transición " + (i + 1) + ": " + estado + " -(" + simbolo[i] + ")> ");

            estadoaux.addAll(estado);
            estado.addAll(macroTransicion(estado, simbolo[i]));
            estado.retainAll(macroTransicion(estadoaux, simbolo[i]));

            eliminarRepeticiones(estado);
            visitados.clear();
            estadoaux.clear();
            for (int j = 0; j < estado.size(); j++) {
                estado.addAll(lambdaClausura(estado.get(j), visitados));
            }

            eliminarRepeticiones(estado);
            System.out.println(estado);
            if (estado.isEmpty()) {
                vMensaje.Mensaje("error", "La cadena no tiene transiciones correctas.\n Comprueba la sintaxis de la cadena.");
                //break;
            }

        }
        return esFinal(estado);
    }

    /**
     * Consume solamente un símbolo. Transiciona con ese simbolo al siguiente
     * estado.
     *
     * @param simbolo Cadena que utiliza para hacer la transición.
     * @param paso Número de transición por la que va el autómata.
     * @param status Estados origen del autómata.
     * @return
     */
    public ArrayList<Integer> siguientePaso(String simbolo, int paso, ArrayList<Integer> status) {
        ArrayList<Integer> estado = new ArrayList();
        ArrayList<Integer> estadoaux = new ArrayList();
        estado.addAll(status);
        ArrayList<Integer> visitados = new ArrayList();

        System.out.print("Transición " + (paso + 1) + ": " + estado + " -(" + simbolo + ")> ");
        estadoaux.addAll(estado);
        estado.addAll(macroTransicion(estado, simbolo));
        estado.retainAll(macroTransicion(estadoaux, simbolo));

        eliminarRepeticiones(estado);

        for (int j = 0; j < estado.size(); j++) {
            estado.addAll(lambdaClausura(estado.get(j), visitados));
        }
        visitados.clear();
        eliminarRepeticiones(estado);
        System.out.println(estado);
        return estado;
    }

    /**
     * Pide por consola los datos para construir al autómata determinista.
     *
     * @return
     * <p>
     * Autómata generado.</p>
     * @throws IOException
     */
    public static Automata pedir() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String cadena;
        Transicion tr;
        TransicionL trL;
        Automata automata = new Automata();
        System.out.println("Introduce los estados:");
        do {
            cadena = br.readLine();
            if (!cadena.isEmpty() && !cadena.startsWith(" ")) {
                automata.agregarEstado(Integer.parseInt(cadena));
            }
        } while (!cadena.isEmpty() && !cadena.startsWith(" "));

        System.out.println("Introduce el estado INICIAL:");
        do {
            cadena = br.readLine();
        } while (!automata.setEstadoInicial(Integer.parseInt(cadena)));

        System.out.println("Introduce los estados FINALES:");
        do {
            cadena = br.readLine();
            if (!cadena.isEmpty() && !cadena.startsWith(" ")) {
                automata.agregarFinal(Integer.parseInt(cadena));
            }
        } while (!cadena.isEmpty() && !cadena.startsWith(" "));
        System.out.println("Introduce las TRANSICIONES NORMALES:");
        int i = 0;
        do {
            System.out.println("Transicion - " + (i + 1));
            tr = automata.pedirTransicion(br);
            if (tr != null) {
                automata.agregarTransicion(tr.getEstadoOrigen(), tr.getSimbolo(), tr.getEstadosDestino());
                i++;
            }
        } while (tr != null);

        System.out.println("Introduce las TRANSICIONES  LAMBDA:");
        int j = 0;
        do {
            System.out.println("Transicion lambda - " + (j + 1));
            trL = automata.pedirLTransicion(br);
            if (trL != null) {
                automata.agregarL_Transicion(trL.getEstadoOrigen(), trL.getEstadoDestino());
                j++;
            }
        } while (trL != null);
        return automata;

    }

    /**
     * Convierte al autómata en un string con el formato del fichero del
     * enunciado de la práctica.
     *
     * @return String
     */
    @Override
    public String toString() {
        String s = "";
        s += "ESTADOS: ";
        for (int i = 0; i < estados.size() - 1; i++) {
            s += estados.get(i) + " ";
        }
        s += estados.get(estados.size() - 1) + "\n";

        s += "INICIAL: " + getEstadoInicial() + "\n";
        s += "FINALES: ";
        for (int i = 0; i < estadosFinales.size() - 1; i++) {
            s += estadosFinales.get(i) + " ";
        }

        s += estadosFinales.get(estadosFinales.size() - 1) + "\n";

        s += "TRANSICIONES:\n";
        for (int i = 0; i < transiciones.size() - 1; i++) {
            s += transiciones.get(i) + "\n";
        }
        s += transiciones.get(transiciones.size() - 1) + "\n";
        for (int i = 0; i < transicionesL.size(); i++) {
            s += transicionesL.get(i).toString() + "\n";
        }

        s += "FIN";
        return s;
    }

    /**
     * Pide por consola una transición para añadirla a la lista de transiciones
     * del autómata.
     *
     * @param br
     * @return
     * @throws IOException
     */
    private Transicion pedirTransicion(BufferedReader br) throws IOException {
        System.out.println("Estado Origen: ");
        String estadoOrigen = "", stringDestino;
        String simbolo;
        try {
            do {
                estadoOrigen = br.readLine();
                if (!estados.contains(Integer.parseInt(estadoOrigen))) {
                    System.out.println("El estado debe pertenecer a la lista de estados");
                }

            } while (!estados.contains(Integer.parseInt(estadoOrigen)));

            System.out.println("Símbolo: ");
            simbolo = br.readLine();

            System.out.println("Estado Destino: ");
            ArrayList<Integer> estadosDestino = new ArrayList();
            do {
                stringDestino = br.readLine();

                if (!estados.contains(Integer.parseInt(stringDestino))) {
                    System.out.println("El estado debe pertenecer a la lista de estados");
                }

                estadosDestino.add(Integer.parseInt(stringDestino));

            } while (!stringDestino.isEmpty() && !stringDestino.startsWith(" ") && estadosDestino.isEmpty() && !estados.contains(Integer.parseInt(stringDestino)));

            return (new Transicion(Integer.parseInt(estadoOrigen), simbolo, estadosDestino));
        } catch (IOException | NumberFormatException e) {
            System.out.println("Debes introducir un número válido.");
            return null;
        }
    }

    /**
     * Pide por consola una &lambda;-transición para añadirla a la lista de
     * transiciones del autómata.
     *
     * @param br
     * @return
     */
    public TransicionL pedirLTransicion(BufferedReader br) {
        System.out.println("Estado Origen: ");
        String estadoOrigen = "", stringDestino;
        try {
            do {
                estadoOrigen = br.readLine();
                if (!estados.contains(Integer.parseInt(estadoOrigen))) {
                    System.out.println("El estado debe pertenecer a la lista de estados");
                }

            } while (!estados.contains(Integer.parseInt(estadoOrigen)));

            System.out.println("Estados Destino: ");
            ArrayList<Integer> estadosDestino = new ArrayList();
            do {
                stringDestino = br.readLine();

                if (!estados.contains(Integer.parseInt(stringDestino))) {
                    System.out.println("El estado debe pertenecer a la lista de estados");
                }

                estadosDestino.add(Integer.parseInt(stringDestino));

            } while (!stringDestino.isEmpty() && !stringDestino.startsWith(" ") && estadosDestino.isEmpty() && !estados.contains(Integer.parseInt(stringDestino)));

            return (new TransicionL(Integer.parseInt(estadoOrigen), estadosDestino));
        } catch (IOException | NumberFormatException e) {
            System.out.println("Debes introducir un número válido.");
            return null;
        }

    }

    /**
     * Realiza una copia del autómata.
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = null;
        try {
            obj = (Automata) super.clone();
            //Copiar las listas
        } catch (CloneNotSupportedException e) {
            System.out.println("No se puede clonar.");
        }
        return obj;
    }

    /**
     * Devuelve el estado inicial del autómata.
     *
     * @return
     */
    public int getEstadoInicial() {
        return inicial;
    }

    /**
     * Establece el estado inicial del autómata.
     *
     * @param inicial Estado inicial del autómata.
     * @return
     */
    public boolean setEstadoInicial(int inicial) {
        if (estados.contains(inicial)) {
            this.inicial = inicial;
            return true;
        } else {
            System.out.println("El estado inicial debe pertenecer al conjunto de estados");
            return false;
        }
    }

    /**
     * Añade un estado a la lista de estados del autómata.
     *
     * @param e1
     */
    public void agregarEstado(int e1) {
        if (!estados.contains(e1)) {
            estados.add(e1);
        }
    }

    /**
     * Añade un estado final a la lista de estados finales del autómata.
     *
     * @param e estado del autómata que quiera añadir a la lista de estados
     * finales.
     * @return
     */
    public boolean agregarFinal(int e) {
        if (!estadosFinales.contains(e)) {
            if (estados.contains(e)) {
                estadosFinales.add(e);
                return true;
            } else {
                System.out.println("El estado final debe pertenecer a los estados");
                return false;
            }
        } else {
            System.out.println("El estado ya pertenece a los estados finales");
            return false;
        }
    }

    /**
     * Abre el selector de ficheros y lee el fichero seleccionado. Comprueba que
     * el fichero esté bien formado.
     *
     * @return
     */
    public boolean leerFichero() //Lee un fichero del disco duro
    {
        boolean resultado = false;
        int check = 0;
        JFileChooser selector = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("AUTO", "auto");
        selector.setFileFilter(filter);

        selector.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = selector.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File fichero = selector.getSelectedFile();
            String[] nombre = fichero.getName().split("\\.");

            if (nombre[1].equals("auto")) {

                try {
                    try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr)) {
                        String lineaActual;
                        int lineasLeidas = 0;
                        while ((lineaActual = br.readLine()) != null) {
                            lineasLeidas++;
                            String linea[] = lineaActual.split(" ");
                            switch (linea[0]) {
                                case "ESTADOS:": //Si la linea empieza por ESTADOS:
                                    for (int i = 1; i < linea.length; i++) {
                                        agregarEstado(Integer.parseInt(linea[i]));
                                    }
                                    check++;
                                    break;
                                case "INICIAL:": //Si la linea empieza por INICIAL:
                                    setEstadoInicial(Integer.parseInt(linea[1]));
                                    check++;
                                    break;
                                case "FINALES:": //Si la linea empieza por FINALES:
                                    for (int i = 1; i < linea.length; i++) {
                                        agregarFinal(Integer.parseInt(linea[i]));
                                    }
                                    check++;
                                    break;
                                case "TRANSICIONES:":
                                    check++;
                                    break;
                                case "FIN":
                                    check++;
                                    break;
                                default:
                                    try {
                                    String transicion[] = lineaActual.split("\t");
                                    transicion[2] = transicion[2].replace("[", "");
                                    transicion[2] = transicion[2].replace("]", "");

                                    if (transicion[1].replace("'", "").equals("lambda")) { //si es lambdatransicion

                                        ArrayList<Integer> destinos = new ArrayList();
                                        String[] destino = transicion[2].split(", ");

                                        for (String destino1 : destino) {
                                            destinos.add(Integer.parseInt(destino1));
                                        }

                                        //System.out.println(transicion[2]);
                                        System.out.println(destinos);
                                        agregarL_Transicion(Integer.parseInt(transicion[0]), destinos);

                                    } else if (!transicion[1].replace("'", "").equals("lambda")) { //si no es lambda transicion y transiciona a mas de un estado

                                        ArrayList<Integer> destinos = new ArrayList();
                                        String[] destino = transicion[2].split(", ");
                                        for (String destino1 : destino) {
                                            destinos.add(Integer.parseInt(destino1));
                                        }

                                        agregarTransicion(Integer.parseInt(transicion[0]), transicion[1].replace("'", ""), destinos);
                                    }
                                } catch (NumberFormatException e) {
                                    vMensaje.Mensaje("error", "Error de sintaxis en el documento. "
                                            + "(En la linea: " + lineasLeidas + ")"
                                            + "\n---------------------------------------------------\n"
                                            + " Compruebe la ventana de ayuda para más información\n"
                                            + " sobre el formato del documento de texto.");
                                    System.out.println(e.getMessage());
                                    return false;
                                }
                                break;

                            }
                        }

                        resultado = (check == 5);
                    }

                } catch (IOException | NumberFormatException e) {
                    vMensaje.Mensaje("error", e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            vMensaje.Mensaje("error", "No se ha seleccionado ningún fichero.");
        }

        return resultado;
    }

    /**
     * Lee el cuadro de texto de la ventana principal.
     * @param texto
     * @return
     */
    public boolean leerFichero(String texto) //Lee un fichero del textArea
    {
        boolean resultado;
        String[] lineas = texto.split("\n");
        String lineaActual = lineas[0];
        int lineasLeidas = 0, check = 0;
        try {
            while (!lineaActual.equals("FIN")) {
                lineaActual = lineas[lineasLeidas];
                String linea[] = lineaActual.split(" ");
                switch (linea[0]) {
                    case "ESTADOS:": //Si la linea empieza por ESTADOS:
                        for (int i = 1; i < linea.length; i++) {
                            agregarEstado(Integer.parseInt(linea[i]));
                        }
                        check++;
                        break;
                    case "INICIAL:": //Si la linea empieza por INICIAL:
                        setEstadoInicial(Integer.parseInt(linea[1]));
                        check++;
                        break;
                    case "FINALES:": //Si la linea empieza por FINALES:
                        for (int i = 1; i < linea.length; i++) {
                            agregarFinal(Integer.parseInt(linea[i]));
                        }
                        check++;
                        break;
                    case "TRANSICIONES:":
                        check++;
                        break;
                    case "FIN":
                        check++;
                        break;
                    default:
                        try {
                        String transicion[] = lineaActual.split("\t");
                        transicion[2] = transicion[2].replace("[", "");
                        transicion[2] = transicion[2].replace("]", "");

                        if (transicion[1].replace("'", "").equals("lambda")) { //si es lambdatransicion

                            ArrayList<Integer> destinos = new ArrayList();
                            String[] destino = transicion[2].split(", ");

                            for (String destino1 : destino) {
                                destinos.add(Integer.parseInt(destino1));
                            }

                            System.out.println(destinos);
                            agregarL_Transicion(Integer.parseInt(transicion[0]), destinos);

                        } else if (!transicion[1].replace("'", "").equals("lambda")) { //si no es lambda transicion y transiciona a mas de un estado

                            ArrayList<Integer> destinos = new ArrayList();
                            String[] destino = transicion[2].split(", ");
                            for (String destino1 : destino) {
                                destinos.add(Integer.parseInt(destino1));
                            }

                            agregarTransicion(Integer.parseInt(transicion[0]), transicion[1].replace("'", ""), destinos);
                        }
                    } catch (NumberFormatException e) {
                        vMensaje.Mensaje("error", "Error de sintaxis en el documento. "
                                + "(En la linea: " + lineasLeidas + ")"
                                + "\n---------------------------------------------------\n"
                                + " Compruebe la ventana de ayuda para más información\n"
                                + " sobre el formato del documento de texto.");
                        System.out.println(e.getMessage());
                        return false;
                    }
                    break;
                }
                lineasLeidas++;
            }
        } catch (NumberFormatException e) {
        }
        resultado = (check == 5);

        return resultado;
    }

    /**
     * Obtiene las transiciones del autómata
     *
     * @return
     */
    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    /**
     * Obtiene las &lambda;-transiciones del autómata.
     *
     * @return
     */
    public ArrayList<TransicionL> getTransicionesL() {
        return transicionesL;
    }

    /**
     * Transforma un autómata a un fichero .dot
     *
     * @param auto Autómata.
     * @param estadosActuales Estados en los que se encuentra el autómata.
     * @param cadena Cadena que lee el autómata.
     * @param pasoActual Paso por el que va leyendo el autómata.
     * @return
     */
    public static File crearDOT(Automata auto, ArrayList<Integer> estadosActuales, String[] cadena, int pasoActual) {

        String texto = AFNDtoDOT(auto, estadosActuales, cadena, pasoActual);
        File f = new File("src\\main\\resources\\automata.dot");

        try {
            if (f.createNewFile()) {
                vMensaje.Mensaje("info", "Fichero creado correctamente.");
            } else {
                System.out.println("El fichero ya existe. Se ha sobreescrito.");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(f);
            //escribir en el fichero a guardar
            os.write(texto.getBytes(), 0, texto.length());
        } catch (IOException e) {
            vMensaje.Mensaje("error", "Error " + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                vMensaje.Mensaje("error", "Error " + e.getMessage());
            }
        }

        return f;
    }

    /**
     * Genera el código en lenguaje DOT del autómata pasado por parámetro.
     *
     * @param auto Autómata que queremos transformar a DOT
     * @param estadosActuales Lista de estados en los que se encuentra
     * actualmente.
     * @param cadena Cadena que leerá el autómata.
     * @param pasoActual Etapa por la que va el autómata.
     * @return
     */
    private static String AFNDtoDOT(Automata auto, ArrayList<Integer> estadosActuales, String[] cadena, int pasoActual) { // label = "&lambda;"
        String texto = "digraph {\n"
                + "\trankdir = LR\n";
        System.out.println("pasoActual: " + pasoActual);
        if (pasoActual == 0) {
            texto += "x -> " + auto.getEstadoInicial() + "[color=red]\n"
                    + "x [shape=none,fontsize=1,margin=0,width=0.01]\n"
                    + auto.getEstadoInicial() + "\n";

        } else if (pasoActual == cadena.length) {
            System.out.println("Fin de cadena");
            texto += "x -> " + auto.getEstadoInicial() + "[color=blue]\n"
                    + "x [shape=none,fontsize=1,margin=0,width=0.01]\n"
                    + auto.getEstadoInicial() + "\n";
        } else {
            texto += "x -> " + auto.getEstadoInicial() + "\n"
                    + "x [shape=none,fontsize=1,margin=0,width=0.01]\n"
                    + auto.getEstadoInicial() + "\n";
        }
        ArrayList<Transicion> transiciones = auto.getTransiciones();
        ArrayList<TransicionL> transicionesL = auto.getTransicionesL();
        //colorea  los estados
        for (int i = 0; i < estadosActuales.size(); i++) {
            texto += estadosActuales.get(i) + " [color=red]\n";

        }

        //Escribe las transiciones normales
        for (int i = 0; i < transiciones.size(); i++) {
            for (int j = 0; j < transiciones.get(i).getEstadosDestino().size(); j++) {
                if (pasoActual != cadena.length && transiciones.get(i).getSimbolo().equals(cadena[(pasoActual)]) && estadosActuales.contains(transiciones.get(i).getEstadoOrigen())) {
                    texto += transiciones.get(i).getEstadoOrigen() + " -> " + transiciones.get(i).getEstadosDestino().get(j)
                            + " [label=" + transiciones.get(i).getSimbolo() + ",color=blue]\n";
                } else {
                    texto += transiciones.get(i).getEstadoOrigen() + " -> " + transiciones.get(i).getEstadosDestino().get(j)
                            + " [label=" + transiciones.get(i).getSimbolo() + "]\n";
                }
            }
        }

        //Escribe las transiciones lambda
        for (int i = 0; i < transicionesL.size(); i++) {
            for (int j = 0; j < transicionesL.get(i).getEstadoDestino().size(); j++) {
                if (!estadosActuales.contains(transicionesL.get(i).getEstadoOrigen())) {
                    texto += transicionesL.get(i).getEstadoOrigen() + " -> " + transicionesL.get(i).getEstadoDestino().get(j)
                            + " [label=\"&lambda;\"]";
                } else {
                    texto += transicionesL.get(i).getEstadoOrigen() + " -> " + transicionesL.get(i).getEstadoDestino().get(j)
                            + " [label=\"&lambda;\",color=red]";
                }
            }
        }

        //le da forma de doble círculo a los estados finales
        for (int i = 0; i < auto.getEstadosFinales().size(); i++) {
            texto += auto.getEstadosFinales().get(i) + "[shape=doublecircle]\n";
        }

        //Llave de final del código.
        texto += "}";

        return texto;
    }

    /**
     * Elimina los elementos repetidos en la lista pasada por parámetro.
     *
     * @param lista Lista a la que queremos eliminar las repeticiones.
     */
    private void eliminarRepeticiones(ArrayList<Integer> lista) {
        HashSet hs = new HashSet();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
        hs.clear();

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Automata a = new Automata();
        a.leerFichero();

        Automata b = a;
        System.out.println(b.getEstados());
    }
}
