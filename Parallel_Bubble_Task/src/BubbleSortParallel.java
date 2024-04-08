public class BubbleSortParallel {
    public static void main(String args[]) {
        new BubbleSortParallel().inicio();
    }

    void inicio() {
        int[] arr = {18, 3, 8, 4, 2, 13, 10, 7, 6, 9};
        int numThreads = 4;

        Thread[] todos = new Thread[numThreads];
        //Creamos un bucle para crear y arrancar cada hilo
        for (int i = 0; i < numThreads; i++) {
            todos[i] = new BubbleSortThread(arr, i, numThreads);
            todos[i].start(); // Se arranca el hilo recién creado
        }
        // Se inicia un bucle para esperar a que todos los hilos terminen
        for (int i = 0; i < numThreads; i++) {
            try {
                todos[i].join(); //Se espera a que el hilo actual (todos[i]) termine su ejecución
            } catch (InterruptedException ex) {
                System.out.println("error" + ex);
            }
        }
        //Luego, usamos el método merge para combinar los resultados parciales de cada hilo y
        // obtener el resultado final ordenado
        merge(arr, numThreads);

        System.out.println("Array despues de ordenar:");
        //Se inicia un bucle for-each para iterar sobre el arreglo ordenado
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

    //Definimos el método merge que se encargara de combinar los resultados parciales de cada hilo
    static void merge(int[] arr, int numThreads) {
        int[] temp = new int[arr.length]; // Creamos un arreglo temporal para almacenar los resultados combinados
        int chunkSize = (arr.length + numThreads - 1) / numThreads; //Se calcula el tamaño de cada subarreglo ordenado

        // Inicializar subarreglos ordenados

        int[][] sortedChunks = new int[numThreads][chunkSize];//  crea un arreglo bidimensional para almacenar
                                                              // los subarreglos ordenados de cada hilo.
        //  El número de filas está determinado por numThreads que es el número de hilos que se están utilizando
        //  para realizar la ordenación paralela mientras que el número de columnas está determinado por chunkSize, que es
        //  el tamaño de cada subarreglo ordenado

        // En el siguiente for copiamos los subarreglos ordenados en el arreglo bidimensional
        for (int i = 0; i < numThreads; i++) {
            //Se copia parte del arreglo original en cada subarreglo ordenado

            // Segun la API de Java
            // System.arraycopy es el método de Java que se utiliza para copiar un rango de elementos de un
            // arreglo a otro
            System.arraycopy(arr, i * chunkSize, sortedChunks[i], 0, Math.min(chunkSize, arr.length - i * chunkSize));
        }

        // Fusionar subarreglos ordenados
        int[] indexes = new int[numThreads]; //arreglo para almacenar los índices de los elementos
                                             // actuales en cada subarreglo ordenado

        //Se inicia un bucle para buscar el valor mínimo entre los subarreglos ordenados
        for (int i = 0; i < arr.length; i++) {
            int minIndex = -1;
            int minValue = Integer.MAX_VALUE;
            for (int j = 0; j < numThreads; j++) {
                if (indexes[j] < sortedChunks[j].length && sortedChunks[j][indexes[j]] < minValue) {
                    minIndex = j;
                    minValue = sortedChunks[j][indexes[j]];
                }
            }
            if (minIndex != -1) {
                temp[i] = minValue;
                indexes[minIndex]++;
            }
        }

        System.arraycopy(temp, 0, arr, 0, arr.length);
    }

    public class BubbleSortThread extends Thread {
        private int[] arr;
        private int start;
        private int numThreads;

        public BubbleSortThread(int[] arr, int start, int numThreads) {
            this.arr = arr;
            this.start = start;
            this.numThreads = numThreads;
        }
        // Implementamos el método run contiene el código que será ejecutado por el hilo
        //Aqui estara el código que implementa el algoritmo de ordenación de burbuja
        @Override
        public void run() {
            int length = arr.length;
            for (int i = start; i < length - 1; i += numThreads) {
                for (int j = 0; j < length - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }
}
