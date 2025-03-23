import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import entity.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.io.File;
import org.apache.commons.io.IOUtils;



import static org.junit.jupiter.api.Assertions.*;

public class Test6173 {
    private final String DEFAULT_PATH = "/src/main/java/";
    private final String DEFAULT_EXPANSION = ".java";
    private final String STUDENT_CLASS_NAME = "LabWork";
    private final Class<java.util.LinkedList> STUDENT_COLLECTION_NAME = java.util.LinkedList.class;


    @Test
    public void haveClassInProgram() {
        assertDoesNotThrow(() -> {
            getEntityByName(STUDENT_CLASS_NAME);
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @ParameterizedTest
    @MethodSource(value = "getFieldsWithTypes")
    public void checkFieldsAndTypes(String className, String fieldName, String fieldType) {
        assertDoesNotThrow(() -> {
            assertTrue(isFieldAndTypeCorrect(getEntityByName(className), fieldName, fieldType), "В классе - " + className + " отсутствует поле или не указано название поля или тип. Названия поля должно быть - " + fieldName + ". Тип поля должен соответствовать - " + fieldType);
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkAddCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertEquals(1, collection.size(), "Добавление работает некорректно");
            LabWork element = collection.stream().findFirst().get();
            checkCorrectElementInCollection(element);
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkErrorInputOnAddCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getWrongAddCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertEquals(1, collection.size(), "Добавление работает некорректно");
            LabWork element = collection.stream().findFirst().get();
            checkCorrectElementInCollection(element);
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkRestrictionInputOnAddCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getRestrictionsAddCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertEquals(1, collection.size(), "Добавление работает некорректно");
            LabWork element = collection.stream().findFirst().get();
            checkCorrectElementInCollection(element);
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkUpdateCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments().append(getUpdateCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertEquals(1, collection.size(), "Обновление работает некорректно");
            LabWork element = collection.stream().findFirst().get();
            checkUpdateCorrectElementInCollection(element);
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkUpdateEmptyCollectionCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getUpdateCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Обновление работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkUpdateWrongStringIdCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getUpdateWrongStringIdCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Обновление работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkUpdateWrongNumberIdCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getUpdateWrongNumberIdCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Обновление работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkUpdateWithoutIdCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getUpdateWithoutIdCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Обновление работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkRemoveCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments().append(getRemoveCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Удаление элемента работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkRemoveEmptyCollectionCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getRemoveCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Удаление элемента работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkRemoveWrongStringIdCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getRemoveWrongStringIdCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Удаление элемента работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkRemoveWrongNumberIdCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getRemoveWrongNumberIdCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Удаление элемента работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkRemoveWithoutIdCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getRemoveWithoutIdCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Удаление элемента работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkClearEmptyCollectionCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getClearCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Очищение коллекции работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkClearNotEmptyCollectionCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments().append(getClearCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertTrue(collection.isEmpty(), "Очищение коллекции работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkExecuteScriptEmptyFileCommand() {
        assertDoesNotThrow(() -> {
            File file = new File("script.txt");
            file.createNewFile();

            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments().append(getExecuteScriptCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            file.delete();
            assertFalse(collection.isEmpty(), "Выполнение скрипта работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkExecuteScriptRecursionCommand() {
        assertDoesNotThrow(() -> {
            File file = new File("script.txt");
            file.createNewFile();
            File file2 = new File("script2.txt");
            file2.createNewFile();

            try (PrintWriter out = new PrintWriter("script.txt")) {
                out.println("clear");
                out.println("execute_script script2.txt");
            }
            try (PrintWriter out = new PrintWriter("script2.txt")) {
                out.println("clear");
                out.println("execute_script script.txt");
            }


            StringBuilder sb = getAddCommandArguments().append(getExecuteScriptCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            file.delete();
            file2.delete();
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkExecuteScriptCommand() {
        assertDoesNotThrow(() -> {
            File file = new File("script.txt");
            file.createNewFile();

            try (PrintWriter out = new PrintWriter("script.txt")) {
                out.println("clear");
            }

            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments().append(getExecuteScriptCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            file.delete();
            assertTrue(collection.isEmpty(), "Выполнение скрипта работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }

    @Test
    public void checkExecuteScriptNotExistsFileCommand() {
        assertDoesNotThrow(() -> {
            Class<?> classWithCollection = getClassWithRequiredCollection();

            StringBuilder sb = getAddCommandArguments().append(getExecuteScriptCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            Field field = getFieldWithRequiredType(classWithCollection);
            field.setAccessible(true);
            java.util.LinkedList<LabWork> collection = (java.util.LinkedList<LabWork>) field.get(classWithCollection.newInstance());
            assertFalse(collection.isEmpty(), "Выполнение скрипта работает некорректно");
        }, "Во время выполнения программы ошибки не должны выпадать");
    }


    @Test
    public void checkSaveCommand() {
        assertDoesNotThrow(() -> {
            File file = new File("collection.txt");
            file.createNewFile();

            StringBuilder sb = getAddCommandArguments().append(getSaveCommandArguments());
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

            try(FileInputStream inputStream = new FileInputStream("collection.txt")) {
                String everything = IOUtils.toString(inputStream);
                file.delete();
                assertFalse(everything.isBlank(), "Сохранение работает некорректно");
            }
        }, "Во время выполнения программы ошибки не должны выпадать");
    }


    @Test
    public void checkSaveEmptyCollectionCommand() {
        assertDoesNotThrow(() -> {
            File file = new File("collection.txt");
            file.createNewFile();

            StringBuilder sb = getSaveCommandArguments();
            System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
            executeMainMethod();

             try(FileInputStream inputStream = new FileInputStream("collection.txt")) {
                 String everything = IOUtils.toString(inputStream);
                 file.delete();
                assertTrue(everything.isBlank(), "Сохранение работает некорректно");
             }
        }, "Во время выполнения программы ошибки не должны выпадать");
    }


    private static void checkCorrectElementInCollection(LabWork element) {
        assertAll(
		() -> assertEquals("pqWoOWWyGf", element.getName()),
		() -> assertEquals(Integer.parseInt("589"), element.getCoordinates().getX()),
		() -> assertEquals(Long.parseLong("-861"), element.getCoordinates().getY()),
		() -> assertEquals(Double.parseDouble("517"), element.getMinimalPoint()),
		() -> assertEquals(Integer.parseInteger("625"), element.getTunedInWorks()),
		() -> assertEquals(Difficulty.INSANE, element.getDifficulty()),
 		() -> assertEquals("NjEgzOXEZO", element.getDiscipline().getName()),
		() -> assertEquals(Long.parseLong("214"), element.getDiscipline().getLectureHours()),
		() -> assertEquals(Integer.parseInt("-122"), element.getDiscipline().getPracticeHours()),
		() -> assertEquals(Long.parseLong("-337"), element.getDiscipline().getSelfStudyHours()),
		() -> assertEquals(Integer.parseInteger("124"), element.getDiscipline().getLabsCount())
        );
    }

    private static void checkUpdateCorrectElementInCollection(LabWork element) {
        assertAll(
		() -> assertEquals("pqWoOWWyGfUpd", element.getName()),
		() -> assertEquals(Integer.parseInt("589"), element.getCoordinates().getX()),
		() -> assertEquals(Long.parseLong("-861"), element.getCoordinates().getY()),
		() -> assertEquals(Double.parseDouble("517"), element.getMinimalPoint()),
		() -> assertEquals(Integer.parseInteger("625"), element.getTunedInWorks()),
		() -> assertEquals(Difficulty.INSANE, element.getDifficulty()),
 		() -> assertEquals("NjEgzOXEZOUpd", element.getDiscipline().getName()),
		() -> assertEquals(Long.parseLong("214"), element.getDiscipline().getLectureHours()),
		() -> assertEquals(Integer.parseInt("-122"), element.getDiscipline().getPracticeHours()),
		() -> assertEquals(Long.parseLong("-337"), element.getDiscipline().getSelfStudyHours()),
		() -> assertEquals(Integer.parseInteger("124"), element.getDiscipline().getLabsCount())
        );
    }

    private void executeMainMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //Название Main класса
        Class<?> clazz = getEntityByName("Main");
        Method meth = clazz.getMethod("main", String[].class);
        String[] params = null; // init params accordingly
        meth.invoke(null, (Object) params);
    }

    private static StringBuilder getAddCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("add").append('\n');
		sb.append("pqWoOWWyGf").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append("INSANE").append('\n');
		sb.append("NjEgzOXEZO").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getClearCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("clear").append('\n');
        return sb;
    }

    private static StringBuilder getExecuteScriptCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("execute_script script.txt").append('\n');
        return sb;
    }

    private static StringBuilder getUpdateCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("update 1").append('\n');
		sb.append("pqWoOWWyGfUpd").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append("INSANE").append('\n');
		sb.append("NjEgzOXEZOUpd").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getUpdateWrongStringIdCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("update ss").append('\n');
		sb.append("pqWoOWWyGfUpd").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append("INSANE").append('\n');
		sb.append("NjEgzOXEZOUpd").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getUpdateWrongNumberIdCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("update 99").append('\n');
		sb.append("pqWoOWWyGfUpd").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append("INSANE").append('\n');
		sb.append("NjEgzOXEZOUpd").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getUpdateWithoutIdCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("update").append('\n');
		sb.append("pqWoOWWyGfUpd").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append("INSANE").append('\n');
		sb.append("NjEgzOXEZOUpd").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getRemoveCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("remove_by_id 1").append('\n');
        return sb;
    }

    private static StringBuilder getRemoveWrongStringIdCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("remove_by_id ss").append('\n');
        return sb;
    }

    private static StringBuilder getRemoveWrongNumberIdCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("remove_by_id 99").append('\n');
        return sb;
    }

    private static StringBuilder getRemoveWithoutIdCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("remove_by_id").append('\n');
        return sb;
    }

    private static StringBuilder getWrongAddCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("add").append('\n');
		sb.append("pqWoOWWyGf").append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append("INSANE").append('\n');
		sb.append("NjEgzOXEZO").append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append("wrong!").append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getRestrictionsAddCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("add").append('\n');
		sb.append('\n').append('\n');
		sb.append('\n').append('\n');
		sb.append("pqWoOWWyGf").append('\n');
		sb.append("-264").append('\n');
		sb.append(Integer.parseInt("589")).append('\n');
		sb.append(Long.parseLong("-861")).append('\n');
		sb.append('\n').append('\n');
		sb.append("-1").append('\n');
		sb.append(Double.parseDouble("517")).append('\n');
		sb.append('\n').append('\n');
		sb.append(Integer.parseInteger("625")).append('\n');
		sb.append('\n').append('\n');
		sb.append("INSANE").append('\n');
		sb.append('\n').append('\n');
		sb.append('\n').append('\n');
		sb.append("NjEgzOXEZO").append('\n');
		sb.append(Long.parseLong("214")).append('\n');
		sb.append(Integer.parseInt("-122")).append('\n');
		sb.append(Long.parseLong("-337")).append('\n');
		sb.append(Integer.parseInteger("124")).append('\n');
        return sb;
    }

    private static StringBuilder getSaveCommandArguments() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append('\n');
        sb.append("save").append('\n');
        return sb;
    }

    private Class getEntityByName(String name) {
        try {
            String executionPath = System.getProperty("user.dir").replace("\\", "/");
            Optional<Path> optionalPath = Files.walk(Paths.get(executionPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().contains(DEFAULT_EXPANSION))
                    .filter(p -> p.getFileName().toString().replace(DEFAULT_EXPANSION, "").equalsIgnoreCase(name))
                    .findFirst();
            String path = optionalPath.map(op -> op.toString().replace("\\", "/")
                            .replace(executionPath, "")
                            .replace(DEFAULT_PATH, "")
                            .replace(DEFAULT_EXPANSION, "")
                            .replace("/", "."))
                    .orElse(null);

            return Class.forName(path);
        } catch (Exception e) {
            throw new RuntimeException("Не получилось найти класс сущности - " + name);
        }
    }

    private boolean isFieldAndTypeCorrect(Class clazz, String fieldName, String fieldType) {
        Field[] fs = clazz.getDeclaredFields(); // получили массив с объектами Field, соответствующие полям класса SomeClass
        if (fs.length == 0) {
            throw new RuntimeException("Поля сущности {" + STUDENT_CLASS_NAME + "} пусты.");
        }
        for (Field f : fs) {
            if (f.getName().equalsIgnoreCase(fieldName) && (f.getType().toString().toLowerCase().contains(fieldType.toLowerCase()) || fieldType.toLowerCase().contains(f.getType().toString().toLowerCase()))) {
                return true;
            }
        }
        return false;
    }

    private Field getFieldWithRequiredType(Class cl) {
        Class<?> myType = java.util.LinkedList.class;
        for (Field field : cl.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(myType)) {
                if (field.getGenericType().getTypeName().contains(STUDENT_CLASS_NAME)) {
                    return field;
                }
            }
        }
        throw new RuntimeException("В лабораторной работе не реализована коллекция по варианту!");
    }

    private Class<?> getClassWithRequiredCollection() {
        String executionPath = System.getProperty("user.dir").replace("\\", "/");
        try {
            List<? extends Class<?>> classesWithCollection = Files.walk(Paths.get(executionPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().substring(Math.max((p.getFileName().toString().length() - DEFAULT_EXPANSION.length()), 0)).equalsIgnoreCase(DEFAULT_EXPANSION))
                    .map(op -> op.toString().replace("\\", "/")
                            .replace(executionPath, "")
                            .replace(DEFAULT_PATH, "")
                            .replace(DEFAULT_EXPANSION, "")
                            .replace("/", "."))
                    .map(path -> {
                        try {
                            return Class.forName(path);
                        } catch (ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(cl -> {
                        for (Field field : cl.getDeclaredFields()) {
                            if (field.getType().isAssignableFrom(STUDENT_COLLECTION_NAME)) {
                                if (field.getGenericType().getTypeName().contains(STUDENT_CLASS_NAME)) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            if (classesWithCollection.size() != 1) {
                throw new RuntimeException();
            }
            return classesWithCollection.get(0);
        } catch (Exception e) {
            throw new RuntimeException("В лабораторной работе не реализована коллекция по варианту!");
        }
    }

    private static Stream<Arguments> getFieldsWithTypes() {
        return Stream.of(
				Arguments.of("LabWork", "id", "int"),
				Arguments.of("LabWork", "name", "String"),
				Arguments.of("LabWork", "coordinates", "Coordinates"),
				Arguments.of("LabWork", "creationDate", "java.time.ZonedDateTime"),
				Arguments.of("LabWork", "minimalPoint", "Double"),
				Arguments.of("LabWork", "tunedInWorks", "Integer"),
				Arguments.of("LabWork", "difficulty", "Difficulty"),
				Arguments.of("LabWork", "discipline", "Discipline"),
				Arguments.of("Coordinates", "x", "int"),
				Arguments.of("Coordinates", "y", "long"),
				Arguments.of("Discipline", "name", "String"),
				Arguments.of("Discipline", "lectureHours", "long"),
				Arguments.of("Discipline", "practiceHours", "int"),
				Arguments.of("Discipline", "selfStudyHours", "long"),
				Arguments.of("Discipline", "labsCount", "Integer")
        );
    }
}
