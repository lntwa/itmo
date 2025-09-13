package org.example.views;

import java.time.LocalDate;
import java.time.LocalDateTime;


import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.GraphicClient;
import org.example.exceptions.InvalidFieldException;
import org.example.models.City;
import org.example.models.Coordinates;
import org.example.models.Human;
import org.example.models.StandardOfLiving;


/**
 * Класс CityInspectorView представляет представление для просмотра и редактирования информации о городе.
 * Он отображает поля и значения организации, а также предоставляет методы для получения отредактированной организации.
 */
public class CityInspectorView {
    private static final int GAP = 3;
    private static final int HORIZONTAL_PADDING = 10;
    private GraphicClient client;
    private ObjectProperty<City> cityToInspectProperty;
    private ReadOnlyBooleanWrapper cityReadyProperty = new ReadOnlyBooleanWrapper(false);
    private BooleanProperty cityIsEditableProperty = new SimpleBooleanProperty(false);

    private Parent view;
    private ValidationField<String> name;
    private ValidationField<Double> x;
    private ValidationField<Long> y;
    private ValidationField<Double> area;
    private ValidationField<Integer> population;
    private ValidationField<Long> metersAboveSeaLevel;
    private ValidationField<Double> timezone;
    private ValidationField<Float> populationDensity;
    private ValidationChoiceBox<StandardOfLiving> standardOfLiving;
    private ValidationField<String> nameGovernor;
    private ValidationField<Long> age;
    private ValidationField<Integer> height;


    /**
     * Создает новый объект `OrganizationInspectorView`.
     *
     * @param client клиентская часть приложения
     * @param cityToInspectProperty свойство, содержащее организацию для просмотра и редактирования
     */
    public CityInspectorView(GraphicClient client, ObjectProperty<City> cityToInspectProperty) {
        this.client = client;
        this.cityToInspectProperty = cityToInspectProperty;
        cityToInspectProperty.addListener((o, oldV, newV) -> fillFields(newV));
        cityIsEditableProperty.bind(Bindings.createBooleanBinding(CityInspectorView.this::organizationIsEditable, client.authProperty(), cityToInspectProperty));
        createAllFields();


        cityIsEditableProperty.bind(Bindings.and(name.valueReadyProperty,
                Bindings.and(x.valueReadyProperty,
                        Bindings.and(y.valueReadyProperty,
                                Bindings.and(area.valueReadyProperty,
                                        Bindings.and(population.valueReadyProperty,
                                                Bindings.and(metersAboveSeaLevel.valueReadyProperty,
                                                        Bindings.and(timezone.valueReadyProperty,
                                                                Bindings.and(populationDensity.valueReadyProperty,
                                                                        Bindings.and(standardOfLiving.valueReadyProperty,
                                                                                Bindings.and(nameGovernor.valueReadyProperty,
                                                                                        Bindings.and(age.valueReadyProperty,
                                                                                                Bindings.and(height.valueReadyProperty, cityIsEditableProperty)))))))))))));


        VBox box = new VBox(GAP);
        box.setPadding(new Insets(GAP, HORIZONTAL_PADDING, GAP, HORIZONTAL_PADDING));
        box.getChildren().addAll(
                name.getComponent(),
                x.getComponent(),
                y.getComponent(),
                area.getComponent(),
                population.getComponent(),
                metersAboveSeaLevel.getComponent(),
                timezone.getComponent(),
                populationDensity.getComponent(),
                standardOfLiving.getComponent(),
                nameGovernor.getComponent(),
                age.getComponent(),
                height.getComponent()
        );
        this.view = box;
    }

    /**
     * Возвращает представление организации.
     *
     * @return представление организации
     */
    public Parent getView() {
        return view;
    }

    /**
     * Возвращает свойство готовности организации.
     * Готовность организации означает, что все поля заполнены корректно и организация может быть сохранена.
     *
     * @return свойство готовности организации
     */
    public ReadOnlyBooleanProperty cityReadyProperty() {
        return cityReadyProperty().getReadOnlyProperty();
    }

    /**
     * Возвращает объект организации с учетом внесенных изменений.
     * Если введены некорректные значения, возвращается исходный объект организации без изменений.
     *
     * @return объект организации
     */
    public City getCity() {
        try {
            City newCity = new City(
                    cityToInspectProperty.get().getId(),
                    name.getValue(),
                    new Coordinates(
                            x.getValue(),
                            y.getValue()
                    ),
                    LocalDate.now(),
                    area.getValue(),
                    population.getValue(),
                    metersAboveSeaLevel.getValue(),
                    timezone.getValue(),
                    populationDensity.getValue(),
                    standardOfLiving.getValue(),
                    new Human(
                            name.getValue(),
                            age.getValue(),
                            height.getValue()
                    )
            );
            newCity.setOwner(cityToInspectProperty.get().getOwner());
            return newCity;
        } catch (InvalidFieldException e) {
            return cityToInspectProperty.get();
        }
    }

    /**
     * Создает все поля.
     */
    private void createAllFields() {
        name = new ValidationField<>("name", x -> (x.isEmpty() ? null : x), City.VALIDATOR::validateName);
        x = new ValidationField<>("annualTurnoverLabel", new NumberStringConverter<>(Float::parseFloat), Organization.VALIDATOR::validateAnnualTurnover);
        y = new ValidationField<>("fullNameLabel", x -> (x.isEmpty() ? null : x), Organization.VALIDATOR::validateFullName);
        area = new ValidationField<>("employeesCountLabel", new NumberStringConverter<>(Long::parseLong), Organization.VALIDATOR::validateEmployeesCount);
        population = new ValidationField<>("xLabel", new NumberStringConverter<>(Float::parseFloat), Coordinates.VALIDATOR::validateX);
        metersAboveSeaLevel = new ValidationField<>("yLabel", new NumberStringConverter<>(Float::parseFloat), Coordinates.VALIDATOR::validateY);
        timezone = new ValidationChoiceBox<>("typeLabel", x -> (x.isEmpty() ? null : OrganizationType.valueOf(x)), Organization.VALIDATOR::validateType);
        populationDensity = new ValidationField<>("yLabel", new NumberStringConverter<>(Float::parseFloat), Coordinates.VALIDATOR::validateY);
        standardOfLiving.setEnumValues(StandardOfLiving.class);
        nameGovernor = new ValidationField<>("streetLabel", x -> (x.isEmpty() ? null : x), Address.VALIDATOR::validateStreet);
        age = new ValidationField<>("streetLabel", x -> (x.isEmpty() ? null : x), Address.VALIDATOR::validateStreet);
        height = new ValidationField<>("streetLabel", x -> (x.isEmpty() ? null : x), Address.VALIDATOR::validateStreet);
    }

    /**
     * Заполняет поля данными организации.
     *
     * @param organization организация
     */
    private void fillFields(City organization) {
        if (organization == null) {
            name.emptyValue();
            x.emptyValue();
            y.emptyValue();
            area.emptyValue();
            population.emptyValue();
            metersAboveSeaLevel.emptyValue();
            timezone.emptyValue();
            populationDensity.emptyValue();
            standardOfLiving.emptyValue();
            nameGovernor.emptyValue();
            age.emptyValue();
            height.emptyValue();
            return;
        }

        nameField.setValue(organization.getName());
        annualTurnoverField.setValue(organization.getAnnualTurnover());
        fullNameField.setValue(organization.getName());
        employeesCountField.setValue(organization.getEmployeesCount());
        xField.setValue(organization.getCoordinates().getX());
        yField.setValue(organization.getCoordinates().getY());
        typeField.setValue(organization.getType());
        streetField.setValue(organization.getAddress().getStreet());
    }


    /**
     * Проверяет, может ли организация редактироваться.
     *
     * @return true, если организация может быть отредактирована, иначе false
     */
    private boolean organizationIsEditable() {
        if (organizationToInspectProperty.get() == null || client.getAuth() == null) {
            return false;
        }

        return organizationToInspectProperty.get().getOwner().equals(client.getAuth().getLogin());
    }

    /**
     * Класс, представляющий поле валидации.
     *
     * @param <T> тип значения поля
     */
    private class ValidationField<T> {
        private StringConverter<T> converter;
        private AbstractValidator<T> validator;
        private Node component;
        private TextField valueField;
        private Label promptLabel = new Label();
        private BooleanProperty valueReadyProperty = new SimpleBooleanProperty(false);

        /**
         * Создает поле валидации.
         *
         * @param localeKey ключ локализации
         * @param converter конвертер значения
         * @param validator валидатор значения
         */
        ValidationField(String localeKey, StringConverter<T> converter, AbstractValidator<T> validator) {
            this.converter = converter;
            this.validator = validator;
            Label fieldLabel = new Label();
            fieldLabel.textProperty().bind(LocaleManager.getObservableStringByKey(localeKey));
            promptLabel.setTextFill(Color.RED);
            valueField = new TextField();
            valueField.editableProperty().bind(organizationIsEditableProperty);
            VBox mainBox = new VBox(GAP);
            mainBox.getChildren().addAll(fieldLabel, valueField, promptLabel);
            component = mainBox;
            valueField.textProperty().addListener((o, oldV, newV) -> validateAndUpdate(newV));
        }

        /**
         * Проверяет и обновляет значение поля.
         *
         * @param newV новое значение
         */
        void validateAndUpdate(String newV) {
            promptLabel.textProperty().unbind();
            promptLabel.setText("");
            valueReadyProperty.set(false);

            if (organizationToInspectProperty.get() == null) {
                return;
            }

            T value = converter.convert(newV);
            if (value == null && !newV.isEmpty()) {
                promptLabel.textProperty().bind(LocaleManager.getObservableStringByKey("invalidValue"));
                return;
            }

            try {
                validator.validate(value);
            } catch (InvalidFieldException e) {
                promptLabel.textProperty().bind(LocaleManager.getObservableStringByKey(e.getLocaleKey()));
                return;
            }

            valueReadyProperty.set(true);
        }

        /**
         * Очищает значение поля.
         */
        void emptyValue() {
            valueField.setText("");
        }

        /**
         * Устанавливает значение поля.
         *
         * @param value значение
         */
        void setValue(T value) {
            valueField.setText(value == null ? "" : value.toString());
            validateAndUpdate(valueField.getText());
        }

        /**
         * Возвращает значение поля.
         *
         * @return значение поля
         */
        T getValue() {
            return converter.convert(valueField.getText());
        }

        /**
         * Возвращает компонент поля.
         *
         * @return компонент поля
         */
        Node getComponent() {
            return component;
        }
    }

    /**
     * Класс, представляющий поле валидации для ChoiceBox.
     *
     * @param <T> тип значения поля
     */
    private class ValidationChoiceBox<T> {
        private StringConverter<T> converter;
        private AbstractValidator<T> validator;
        private Node component;
        private ChoiceBox<T> valueChoiceBox;
        private Label promptLabel = new Label();
        private BooleanProperty valueReadyProperty = new SimpleBooleanProperty(false);

        /**
         * Создает поле валидации для ChoiceBox.
         *
         * @param localeKey ключ локализации
         * @param converter конвертер значения
         * @param validator валидатор значения
         */
        ValidationChoiceBox(String localeKey, StringConverter<T> converter, AbstractValidator<T> validator) {
            this.converter = converter;
            this.validator = validator;
            Label fieldLabel = new Label();
            fieldLabel.textProperty().bind(LocaleManager.getObservableStringByKey(localeKey));
            promptLabel.setTextFill(Color.RED);
            valueChoiceBox = new ChoiceBox<>();
            valueChoiceBox.disableProperty().bind(organizationIsEditableProperty.not());
            VBox mainBox = new VBox(GAP);
            valueChoiceBox.setMaxWidth(Double.MAX_VALUE);
            mainBox.getChildren().addAll(fieldLabel, valueChoiceBox, promptLabel);
            component = mainBox;
            valueChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validateAndUpdate(newValue));
        }

        /**
         * Проверяет и обновляет значение поля.
         *
         * @param newValue новое значение
         */
        void validateAndUpdate(T newValue) {
            promptLabel.textProperty().unbind();
            promptLabel.setText("");
            valueReadyProperty.set(false);

            if (organizationToInspectProperty.get() == null) {
                return;
            }

            if (newValue == null) {
                promptLabel.textProperty().bind(LocaleManager.getObservableStringByKey("organizationTypeNotEmpty"));
                return;
            }

            try {
                validator.validate(newValue);
            } catch (InvalidFieldException e) {
                promptLabel.textProperty().bind(LocaleManager.getObservableStringByKey(e.getLocaleKey()));
                return;
            }

            valueReadyProperty.set(true);
        }

        /**
         * Очищает значение поля.
         */
        void emptyValue() {
            valueChoiceBox.getSelectionModel().clearSelection();
        }

        /**
         * Устанавливает значение поля.
         *
         * @param value значение
         */
        void setValue(T value) {
            valueChoiceBox.setValue(value);
            validateAndUpdate(value);
        }

        public void setEnumValues(Class<T> enumClass) {
            T[] values = enumClass.getEnumConstants();
            valueChoiceBox.setItems(FXCollections.observableArrayList(values));
        }


        /**
         * Возвращает значение поля.
         *
         * @return значение поля
         */
        T getValue() {
            return valueChoiceBox.getValue();
        }

        /**
         * Возвращает компонент поля.
         *
         * @return компонент поля
         */
        Node getComponent() {
            return component;
        }
    }
}