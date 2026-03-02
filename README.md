# 🎵 NeoTune

Aplicación musical para **gestionar y reproducir música**, con organización por **artistas, álbumes y playlists**.  
Interfaz simple y enfocada en una experiencia de uso clara.

---

## 📌 Contenido
- [Descripción](#-descripción)
- [Funciones](#-funciones)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Base de datos](#-base-de-datos)
- [Tecnologías](#-tecnologías)
- [Instalación y ejecución](#-instalación-y-ejecución)
- [Mejoras futuras](#-mejoras-futuras)
- [Autor](#-autor)

---

## 🧾 Descripción
**NeoTune** es un proyecto orientado a la **gestión de música digital**. Permite organizar contenido musical y acceder rápidamente a canciones mediante búsqueda y filtros.

---

## ✅ Funciones
- 🎤 Gestión de artistas  
- 💿 Gestión de álbumes  
- 🎶 Gestión de canciones  
- 📂 Playlists (creación y administración)  
- 🔎 Búsqueda de canciones  
- 🖥️ Interfaz amigable

---

## 🏗️ Estructura del proyecto
> Estructura recomendada (puedes ajustarla según tu repo).

```text
NeoTune/
├─ src/
│  ├─ main/
│  ├─ models/
│  ├─ controllers/
│  └─ views/
├─ database/
│  └─ scripts.sql
├─ assets/
│  └─ images/
└─ README.md
```

---

## 🗄️ Base de datos
Tablas principales sugeridas:
- **Usuario**
- **Canción**
- **Artista**
- **Álbum**
- **Playlist**
- **Playlist_Canción** (relación muchos-a-muchos)
- **Reproducción** (historial / métricas)

> Si ya tienes un script en `database/scripts.sql`, úsalo como fuente principal.

---

## 🛠️ Tecnologías
- **Lenguaje:** Java  
- **IDE:** Apache NetBeans (recomendado)  
- **BD:** SQL Server (opcional, si tu proyecto usa persistencia)  
- **Control de versiones:** Git / GitHub  

---

## ⚙️ Instalación y ejecución
1. **Clona el repositorio**
   ```bash
   git clone https://github.com/NilverTI/NeoTune.git
   ```

2. **Abre el proyecto en NetBeans**
   - `File > Open Project` y selecciona la carpeta **NeoTune**.

3. **(Opcional) Configura la base de datos**
   - Ejecuta `database/scripts.sql` en tu gestor de SQL Server.
   - Ajusta tu cadena de conexión en el proyecto (si aplica).

4. **Ejecuta el proyecto**
   - Click derecho sobre el proyecto → **Run**.

---

## 📈 Mejoras futuras
- ⭐ Sistema de recomendaciones
- 🌙 Modo oscuro
- 📊 Estadísticas de reproducción
- 🌐 Integración con APIs externas
- 📱 Versión móvil

---

## 👨‍💻 Autor
**Nilver Tantalean Inga** — Ingeniería de Sistemas

---

## 🎧 NeoTune
**Feel the Music. Live the Moment.**
