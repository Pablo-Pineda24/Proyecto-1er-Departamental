import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:io';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart' as path; // Usa un alias para evitar conflictos

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Menú Principal',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routes: {
        '/': (context) => const MenuPrincipal(),
        '/hola_mundo': (context) => const HolaMundo(),
        '/practica_1': (context) => const CounterPage(),
        '/practica_2': (context) => const ClickCounterPage(),
        '/practica_3': (context) => const Practica3(),
        '/practica_4': (context) => const Practica4(),
        '/ejemplo_4': (context) => const Ejemplo4(), // Ruta para el Ejemplo 4
      },
      initialRoute: '/',
    );
  }
}

class MenuPrincipal extends StatelessWidget {
  const MenuPrincipal({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Menú Principal'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                'Menú Principal',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 32),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/hola_mundo');
                },
                child: const Text('Hola Mundo'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/practica_1');
                },
                child: const Text('Práctica 1'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/practica_2');
                },
                child: const Text('Práctica 2'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/practica_3');
                },
                child: const Text('Práctica 3'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/practica_4');
                },
                child: const Text('Práctica 6'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(
                      context, '/ejemplo_4'); // Navegar al Ejemplo 4
                },
                child: const Text('Practica 5'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class HolaMundo extends StatelessWidget {
  const HolaMundo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Hola Mundo'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              '¡Hola, mundo!',
              style: TextStyle(fontSize: 24),
            ),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text('Volver al Menú Principal'),
            ),
          ],
        ),
      ),
    );
  }
}

class CounterPage extends StatefulWidget {
  const CounterPage({Key? key}) : super(key: key);

  @override
  State<CounterPage> createState() => _CounterPageState();
}

class _CounterPageState extends State<CounterPage> {
  int counter = 0;

  void incrementCounter() {
    setState(() {
      counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Práctica 1'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                'Has presionado el botón: $counter veces',
                style: const TextStyle(fontSize: 20),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: incrementCounter,
                child: const Text('Incrementar Contador'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamedAndRemoveUntil(
                    context,
                    '/',
                    (route) => false,
                  );
                },
                child: const Text('Volver al Menú Principal'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class ClickCounterPage extends StatefulWidget {
  const ClickCounterPage({Key? key}) : super(key: key);

  @override
  State<ClickCounterPage> createState() => _ClickCounterPageState();
}

class _ClickCounterPageState extends State<ClickCounterPage> {
  int clickCount = 0;
  bool isTimerRunning = false;
  int timeRemaining = 10;
  Timer? timer;

  void startTimer() {
    setState(() {
      clickCount = 0;
      isTimerRunning = true;
      timeRemaining = 10;
    });

    timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      setState(() {
        if (timeRemaining > 0) {
          timeRemaining--;
        } else {
          isTimerRunning = false;
          timer.cancel();
          showResults();
        }
      });
    });
  }

  void incrementCounter() {
    if (isTimerRunning) {
      setState(() {
        clickCount++;
      });
    }
  }

  void showResults() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Tiempo terminado'),
        content: Text('Hiciste $clickCount clics.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cerrar'),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    timer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Práctica 2'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                'Contador de clics',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 32),
              Text(
                'Tiempo restante: $timeRemaining segundos',
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: isTimerRunning ? incrementCounter : null,
                child: const Text('Hacer clic'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: isTimerRunning ? null : startTimer,
                child: const Text('Iniciar temporizador'),
              ),
              const SizedBox(height: 16),
              Text(
                'Número de clics: $clickCount',
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 32),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamedAndRemoveUntil(
                    context,
                    '/',
                    (route) => false,
                  );
                },
                child: const Text('Volver al Menú Principal'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class Practica3 extends StatefulWidget {
  const Practica3({Key? key}) : super(key: key);

  @override
  State<Practica3> createState() => _Practica3State();
}

class _Practica3State extends State<Practica3> {
  int clickCount = 0;
  int timeRemaining = 10;
  List<int> scores = [];
  bool isClickButtonEnabled = false;
  Timer? timer;

  @override
  void initState() {
    super.initState();
    _loadScores();
  }

  Future<void> _loadScores() async {
    final prefs = await SharedPreferences.getInstance();
    final savedScores = prefs.getStringList('scores') ?? [];
    setState(() {
      scores = savedScores.map((score) => int.parse(score)).toList();
      scores.sort((a, b) => b.compareTo(a));
    });
  }

  Future<void> _saveScores() async {
    final prefs = await SharedPreferences.getInstance();
    final scoresAsStrings = scores.map((score) => score.toString()).toList();
    await prefs.setStringList('scores', scoresAsStrings);
  }

  void startGame() {
    timer?.cancel();
    setState(() {
      clickCount = 0;
      timeRemaining = 10;
      isClickButtonEnabled = true;
    });

    timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      setState(() {
        if (timeRemaining > 0) {
          timeRemaining--;
        } else {
          timer.cancel();
          isClickButtonEnabled = false;
          scores.add(clickCount);
          scores.sort((a, b) => b.compareTo(a));
          _saveScores();
        }
      });
    });
  }

  void incrementClickCount() {
    if (isClickButtonEnabled) {
      setState(() {
        clickCount++;
      });
    }
  }

  void clearHistory() {
    setState(() {
      scores.clear();
      _saveScores();
    });
  }

  @override
  void dispose() {
    timer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Práctica 3'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            children: [
              Text(
                'Has hecho clic $clickCount veces',
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 16),
              Text(
                timeRemaining > 0
                    ? 'Tiempo restante: $timeRemaining segundos'
                    : 'Tiempo terminado',
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 24),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: startGame,
                    child: const Text('Iniciar'),
                  ),
                  const SizedBox(width: 16),
                  ElevatedButton(
                    onPressed:
                        isClickButtonEnabled ? incrementClickCount : null,
                    child: const Text('¡Haz Clic!'),
                  ),
                ],
              ),
              const SizedBox(height: 32),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'Top Scores:',
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  ElevatedButton(
                    onPressed: clearHistory,
                    style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 12),
                    ),
                    child: const Text('Borrar Historial'),
                  ),
                ],
              ),
              const SizedBox(height: 8),
              Container(
                width: double.infinity,
                height: 150,
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                  borderRadius: BorderRadius.circular(4),
                ),
                child: scores.isEmpty
                    ? const Text('No hay puntuaciones registradas')
                    : ListView.builder(
                        itemCount: scores.length > 5 ? 5 : scores.length,
                        itemBuilder: (context, index) {
                          return Text('${index + 1}. ${scores[index]}');
                        },
                      ),
              ),
              const SizedBox(height: 16),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.pushNamedAndRemoveUntil(
                      context,
                      '/',
                      (route) => false,
                    );
                  },
                  child: const Text('Regresar al Menú Principal'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class Practica4 extends StatefulWidget {
  const Practica4({Key? key}) : super(key: key);

  @override
  _Practica4State createState() => _Practica4State();
}

class _Practica4State extends State<Practica4> {
  int clickCount = 0;
  int timeRemaining = 10;
  bool isGameRunning = false;
  List<int> topScores = [];
  late DatabaseHelper dbHelper;

  @override
  void initState() {
    super.initState();
    dbHelper = DatabaseHelper();
    _loadTopScores();
  }

  void _loadTopScores() async {
    final scores = await dbHelper.getTopScores();
    setState(() {
      topScores = scores;
    });
  }

  void _startGame() {
    setState(() {
      clickCount = 0;
      timeRemaining = 10;
      isGameRunning = true;
    });

    Future.doWhile(() async {
      await Future.delayed(const Duration(seconds: 1));
      if (mounted) {
        setState(() {
          timeRemaining--;
        });
      }

      if (timeRemaining <= 0) {
        isGameRunning = false;
        await dbHelper.insertScore(clickCount);
        _loadTopScores();
        return false;
      }
      return isGameRunning;
    });
  }

  void _incrementCount() {
    if (isGameRunning) {
      setState(() {
        clickCount++;
      });
    }
  }

  void _clearScores() async {
    await dbHelper.clearAllScores();
    _loadTopScores();
  }

  void _exportScores() async {
    if (await _checkPermission()) {
      final scores = await dbHelper.getAllScores();
      if (scores.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('No hay puntajes para exportar')),
        );
        return;
      }

      try {
        final content = StringBuffer("PUNTAJES DEL JUEGO DE CLICS\n\n");
        for (int i = 0; i < scores.length; i++) {
          content.write("${i + 1}. ${scores[i]}\n");
        }

        final fileName =
            "puntajes_clicker_${DateTime.now().millisecondsSinceEpoch}.txt";
        final directory = await _getExportDirectory();
        final file = File(
            path.join(directory.path, fileName)); // Usa path.join con el alias
        await file.writeAsString(content.toString());
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Archivo guardado en: ${file.path}')),
        );
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error al crear el archivo: $e')),
        );
      }
    }
  }

  Future<Directory> _getExportDirectory() async {
    if (Platform.isAndroid) {
      if (await Permission.storage.request().isGranted) {
        if (int.parse(Platform.operatingSystemVersion.split(' ')[0]) >= 10) {
          return await getExternalStorageDirectory() ??
              await getApplicationDocumentsDirectory();
        } else {
          return Directory('/storage/emulated/0/Download');
        }
      }
    }
    return await getApplicationDocumentsDirectory();
  }

  Future<bool> _checkPermission() async {
    if (Platform.isAndroid) {
      if (int.parse(Platform.operatingSystemVersion.split(' ')[0]) >= 10) {
        return true;
      }
      if (await Permission.storage.request().isGranted) {
        return true;
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
              content: Text(
                  'Permiso de almacenamiento denegado. No se puede exportar.')),
        );
        return false;
      }
    }
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Práctica 4 - Juego de Clics'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                'Tiempo restante: $timeRemaining segundos',
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 16),
              Text(
                'Has hecho clic $clickCount veces',
                style: const TextStyle(fontSize: 18),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: !isGameRunning ? _startGame : null,
                child: const Text('Iniciar'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: isGameRunning ? _incrementCount : null,
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(200, 60),
                ),
                child: const Text('¡Haz clic!'),
              ),
              const SizedBox(height: 16),
              const Text(
                'Top Scores:',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 8),
              SizedBox(
                height: 120,
                child: topScores.isEmpty
                    ? const Text('No hay puntajes registrados')
                    : ListView.builder(
                        itemCount: topScores.length,
                        itemBuilder: (context, index) {
                          return Text(
                            '${index + 1}. ${topScores[index]}',
                            style: const TextStyle(fontSize: 16),
                          );
                        },
                      ),
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: _clearScores,
                    child: const Text('Limpiar puntajes'),
                  ),
                  const SizedBox(width: 16),
                  ElevatedButton(
                    onPressed: _exportScores,
                    child: const Text('Exportar puntajes'),
                  ),
                ],
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  Navigator.pop(context);
                },
                child: const Text('Volver al menú principal'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class Ejemplo4 extends StatefulWidget {
  const Ejemplo4({Key? key}) : super(key: key);

  @override
  State<Ejemplo4> createState() => _Ejemplo4State();
}

class _Ejemplo4State extends State<Ejemplo4> {
  final dbHelper = NotasDatabaseHelper.instance; // Usar NotasDatabaseHelper
  final TextEditingController _tituloController = TextEditingController();
  final TextEditingController _contenidoController = TextEditingController();
  String _notasText = "Las notas guardadas aparecerán aquí";

  @override
  void dispose() {
    _tituloController.dispose();
    _contenidoController.dispose();
    super.dispose();
  }

  void _guardarNota() async {
    String titulo = _tituloController.text;
    String contenido = _contenidoController.text;

    if (titulo.isNotEmpty && contenido.isNotEmpty) {
      int id = await dbHelper.agregarNota(titulo, contenido);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Nota guardada con ID: $id')),
        );
      }

      // Limpiar campos después de guardar
      _tituloController.clear();
      _contenidoController.clear();
    } else {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Por favor completa todos los campos')),
        );
      }
    }
  }

  void _mostrarNotas() async {
    List<Map<String, dynamic>> notas = await dbHelper.getAllNotas();

    if (notas.isEmpty) {
      setState(() {
        _notasText = "No hay notas guardadas";
      });
    } else {
      final notasList = StringBuffer();
      for (var nota in notas) {
        int id = nota[NotasDatabaseHelper.columnId];
        String titulo = nota[NotasDatabaseHelper.columnTitulo];
        String contenido = nota[NotasDatabaseHelper.columnContenido];

        notasList.write("ID: $id - $titulo\n$contenido\n\n");
      }

      setState(() {
        _notasText = notasList.toString();
      });
    }
  }

  void _navegarAMenuPrincipal() {
    Navigator.pushNamedAndRemoveUntil(
      context,
      '/',
      (route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Notas App'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _tituloController,
              decoration: const InputDecoration(
                hintText: 'Título de la nota',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 8),
            TextField(
              controller: _contenidoController,
              decoration: const InputDecoration(
                hintText: 'Contenido de la nota',
                border: OutlineInputBorder(),
              ),
              minLines: 3,
              maxLines: 5,
            ),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: _guardarNota,
                    child: const Text('Guardar Nota'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    onPressed: _mostrarNotas,
                    child: const Text('Mostrar Notas'),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: _navegarAMenuPrincipal,
                    child: const Text('Regresar al Menú Principal'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    onPressed: _navegarAMenuPrincipal,
                    child: const Text('Ir a Menú Principal'),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Expanded(
              child: SingleChildScrollView(
                child: Text(
                  _notasText,
                  style: const TextStyle(fontSize: 16),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class NotasDatabaseHelper {
  static const _databaseName = "notasDB";
  static const _databaseVersion = 1;

  static const table = 'notas';
  static const columnId = 'id';
  static const columnTitulo = 'titulo';
  static const columnContenido = 'contenido';

  // Singleton class
  NotasDatabaseHelper._privateConstructor();
  static final NotasDatabaseHelper instance =
      NotasDatabaseHelper._privateConstructor();

  static Database? _database;
  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDatabase();
    return _database!;
  }

  Future<Database> _initDatabase() async {
    String databasePath = path.join(await getDatabasesPath(), _databaseName);
    return await openDatabase(
      databasePath,
      version: _databaseVersion,
      onCreate: _onCreate,
    );
  }

  Future<void> _onCreate(Database db, int version) async {
    await db.execute('''
      CREATE TABLE $table (
        $columnId INTEGER PRIMARY KEY,
        $columnTitulo TEXT NOT NULL,
        $columnContenido TEXT NOT NULL
      )
    ''');
  }

  Future<int> agregarNota(String titulo, String contenido) async {
    Database db = await database;
    Map<String, dynamic> row = {
      columnTitulo: titulo,
      columnContenido: contenido
    };
    return await db.insert(table, row);
  }

  Future<List<Map<String, dynamic>>> getAllNotas() async {
    Database db = await database;
    return await db.query(table);
  }
}

class DatabaseHelper {
  static final DatabaseHelper _instance = DatabaseHelper._internal();
  static Database? _database;

  factory DatabaseHelper() => _instance;

  DatabaseHelper._internal();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDatabase();
    return _database!;
  }

  Future<Database> _initDatabase() async {
    final dbPath = await getDatabasesPath();
    final dbFile = path.join(dbPath, 'scores.db'); // Usa path.join con el alias
    return await openDatabase(
      dbFile,
      version: 1,
      onCreate: (db, version) {
        return db.execute(
          'CREATE TABLE scores(id INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER)',
        );
      },
    );
  }

  Future<void> insertScore(int score) async {
    final db = await database;
    await db.insert(
      'scores',
      {'score': score},
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<List<int>> getTopScores() async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'scores',
      orderBy: 'score DESC',
      limit: 5,
    );

    return List.generate(maps.length, (i) {
      return maps[i]['score'] as int;
    });
  }

  Future<List<int>> getAllScores() async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'scores',
      orderBy: 'score DESC',
    );

    return List.generate(maps.length, (i) {
      return maps[i]['score'] as int;
    });
  }

  Future<void> clearAllScores() async {
    final db = await database;
    await db.delete('scores');
  }
}