# Gestor de Alimentos - Aplicación Android

## Descripción General

**Gestor de Alimentos** es una aplicación móvil desarrollada en **Java** para el sistema operativo **Android**, que permite registrar, actualizar, eliminar y visualizar alimentos almacenados localmente mediante una base de datos **SQLite**.  
El objetivo principal de este proyecto es implementar un sistema CRUD completo que gestione información de productos alimenticios sin necesidad de conexión a internet, ideal para el aprendizaje y práctica de desarrollo de aplicaciones nativas con persistencia local de datos.

---

## Características Principales

- Registro de alimentos con campos como nombre, descripción, cantidad y precio.  
- Visualización en tabla (RecyclerView o ListView) de los registros almacenados.  
- Actualización y eliminación de registros existentes mediante eventos de selección.  
- Persistencia local usando SQLite a través de una clase `DBHelper` personalizada.  
- Validación básica de entradas antes del almacenamiento.  
- Interfaz sencilla y funcional diseñada con XML (Android Layouts).  

---

## Arquitectura y Estructura del Proyecto

La aplicación sigue una estructura organizada por actividades y clases auxiliares, separando la lógica del acceso a datos:


<img width="315" height="599" alt="image" src="https://github.com/user-attachments/assets/02b74fdc-001e-4d2e-87bb-f8ef6601172e" />


---

## Tecnologías y Herramientas

- Lenguaje: Java  
- Base de datos: SQLite (nativa de Android)  
- Entorno de desarrollo: Android Studio  
- Versión de Gradle: según configuración de proyecto  
- Versión mínima de Android (minSdkVersion): 24 (Android 7.0 Nougat)  
- Versión objetivo (targetSdkVersion): 34 (Android 14)  
- Sistema operativo de desarrollo: Windows 10  
- Control de versiones: Git y GitHub  

---

## Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/JGuajardoG/SQLite-practica.git

2. Abrir en Android Studio

Ir a File → Open...

Seleccionar la carpeta del proyecto.

Esperar a que Gradle sincronice dependencias.

3. Ejecutar la aplicación

Conectar un dispositivo físico Android o iniciar un emulador.

Presionar Run ▶️ en Android Studio.

| Acción     | Descripción                             | Clase principal           |
| :--------- | :-------------------------------------- | :------------------------ |
| Crear      | Permite registrar un nuevo alimento     | `AddFoodActivity.java`    |
| Leer       | Muestra los registros en la tabla       | `MainActivity.java`       |
| Actualizar | Modifica datos de un alimento existente | `UpdateFoodActivity.java` |
| Eliminar   | Borra un alimento mediante confirmación | `DeleteFoodActivity.java` |

Autores

Proyecto desarrollado por estudiantes de Ingeniería en Informática:

Jeremy Guajardo

Jade Conrido

Leonardo Pérez

Repositorio

Accede al proyecto completo en el siguiente enlace:
https://github.com/JGuajardoG/SQLite-practica

Licencia

Este proyecto se encuentra bajo licencia MIT, lo que permite su uso, copia, modificación y distribución con fines educativos y académicos.

Notas Adicionales

La aplicación no requiere conexión a internet, dado que utiliza almacenamiento local.

Se diseñó con fines educativos para la práctica de desarrollo Android con SQLite.

Compatible con dispositivos Android 7.0 o superior.
