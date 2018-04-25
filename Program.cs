using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace SrEjercicio1
{
    class Program
    {
        static readonly private Random caminoaleatorio = new Random();
        static readonly private Random sueñoAleatorio = new Random();
        static readonly private object l = new object();
        static bool continua = true;
        static bool salir = true;
        static bool[] tropiezo;
        //static bool tramposoAle = true;
        static int cabGanador, dinero, numCaballos;
        static int cabApostado = -1;
        static int Monedero = 500;
        static int tamañoCarrera = 70;


        public static void MoveCaballo(object posY)
        {
            int posicionY = (int)posY;
            bool tropiezoDentroHilo = (bool)tropiezo[posicionY];
            int pasos = 0;
            while (continua)
            {
                if (!tropiezoDentroHilo)
                {

                    if (tamañoCarrera != 1)
                    {
                        lock (l)
                        {
                            Console.SetCursorPosition(pasos, posicionY + 5);
                            Console.Write("{0,13}", "Horse");
                        }

                        Thread.Sleep(sueñoAleatorio.Next(1, 600));
                    }
                    if (tamañoCarrera < pasos)
                    {
                        continua = false;
                        Console.SetCursorPosition(0, 3);
                        Console.WriteLine("Gano el caballo " + posicionY + "");
                        Console.ReadKey();
                        pasos = 0;
                        Console.Clear();
                        lock (l)
                        {
                            Monitor.Pulse(l);
                        }
                        cabGanador = posicionY;
                    }
                    pasos++;
                    pasos += caminoaleatorio.Next(1, 8);
                    Trap(tropiezoDentroHilo);
                }
                else
                {
                    Console.WriteLine("Tropezo");
                }
            }
        }

        public static void MensajeMonedero()
        {
            Console.SetCursorPosition(70, 0);
            Console.WriteLine("Monedero:" + Monedero + "  Leuros");
        }

        public static int Menu()
        {
            int eleccion = 3;
                MensajeMonedero();
                do
                {
                    try
                    {
                        Console.WriteLine("==============\n" +
                        "    MENU     =\n" +
                        "==============\n" +
                        "1) Apostar\n" +
                        "2) Salir");
                    eleccion = Convert.ToInt32(Console.ReadLine());
                    Console.Clear();
                    if (eleccion <= 0 || eleccion > 2)
                    {
                        Console.WriteLine("Elige una opcion del menu valida");
                        Console.ReadKey();
                        Console.Clear();
                    }
                    }
                    catch (FormatException)
                    {
                        Console.WriteLine("Introduce un numero de la lista");
                        Console.ReadKey();
                        Console.Clear();
                    }
                } while (eleccion <= 0 || eleccion > 2);
            return eleccion;
        }

        public void PedirDatos()
        {
            continua = true;
            do
            {
                try
                {
                    Console.WriteLine("¿Cuantos caballos quieres que participen? (1 a 5)");
                    MensajeMonedero();
                    numCaballos = Convert.ToInt32(Console.ReadLine());
                    if (numCaballos <= 0 || numCaballos > 5)
                    {
                        Console.WriteLine("Solo se permiten carreras de 1 a 5 caballos");
                        Console.ReadKey();
                        Console.Clear();

                    }
                }
                catch (FormatException)
                {
                    Console.WriteLine("Introduce un numero de caballos");
                    Console.ReadKey();
                    Console.Clear();
                }
            } while (numCaballos <= 0 || numCaballos > 5);
            Console.Clear();
            do
            {
                try
                {
                    Console.WriteLine("¿Porque caballo quieres apostar? (0 al {0})", numCaballos - 1);
                    MensajeMonedero();
                    cabApostado = Convert.ToInt32(Console.ReadLine());
                    if (cabApostado < 0 || cabApostado >= numCaballos)
                    {
                        Console.WriteLine("Apuesta por un caballo que este dentro de la carrera");
                        Console.ReadKey();
                        Console.Clear();
                    }
                }
                catch (FormatException)
                {
                    Console.WriteLine("Elige un caballo por el cual apostar");
                    Console.ReadKey();
                    Console.Clear();
                }
            } while (cabApostado < 0 || cabApostado >= numCaballos);
            Console.Clear();
            do
            {
                try
                {
                    Console.WriteLine("¿Cuanto quieres apostar en la carrera? (1 - 2000 PowerCoins)");
                    MensajeMonedero();
                    dinero = Convert.ToInt32(Console.ReadLine());
                    Monedero = Monedero - dinero;
                    if (dinero <= 0 || dinero > 2000)
                    {
                        Console.WriteLine("El dinero que se permite apostar en estas carreras es de 1 a 2000 ");
                        Console.ReadKey();
                        Console.Clear();
                    }
                }catch (FormatException)
                {
                    Console.WriteLine("Escriba alguna cantidad para apostar");
                    Console.ReadKey();
                    Console.Clear();
                }
            } while (dinero <= 0 || dinero > 2000);
            Console.Clear();
            Console.WriteLine("La carrera ha comenzado con {0} caballos(!!)",numCaballos);
            MensajeMonedero();
        }

        public void Elecciones(int opcion)
        {
            switch (opcion)
            {
                case 1:
                    PedirDatos();
                    break;
                case 2:
                    Console.WriteLine("Saliendo...");
                    Console.ReadKey();
                    salir = false;
                    break;
            }
        }

        public static void Trap(object tropiezo)
        {
            bool tropiezoDentroHilo = (bool)tropiezo;
            tropiezoDentroHilo = true;
        }

        static void Main(string[] args)
        {
            Program eje1 = new Program();
            while (salir)
            {
                 //   eje1.Elecciones(Menu());
                    Thread tramposo = new Thread(Trap);
                    Thread[] hilo = new Thread[numCaballos];
                    tropiezo = new bool[numCaballos];
                    tramposo.Start();
                    for (int i = 0; i < numCaballos; i++)
                    {
                        tropiezo[i] = false;
                        hilo[i] = new Thread(MoveCaballo);
                        hilo[i].Start(i);
                }
                if (salir)
                {
                    lock (l)
                    {
                        Monitor.Wait(l);
                    }
                }
                if (cabApostado == cabGanador)
                {
                    Monedero += dinero * 2;
                }
            }
        }
    }
}
