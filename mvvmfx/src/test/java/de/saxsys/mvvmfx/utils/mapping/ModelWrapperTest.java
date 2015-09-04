package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelWrapperTest {
	
	
	@Test
	public void testWithGetterAndSetter() {
		Person person = new Person();
		person.setName("horst");
		person.setAge(32);
		person.setNicknames(Arrays.asList("captain"));
		
		ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);
		
		final StringProperty nameProperty = personWrapper.field(Person::getName, Person::setName);
		final IntegerProperty ageProperty = personWrapper.field(Person::getAge, Person::setAge);
		final ListProperty<String> nicknamesProperty = personWrapper.field(Person::getNicknames, Person::setNicknames);
		
		assertThat(nameProperty.getValue()).isEqualTo("horst");
		assertThat(ageProperty.getValue()).isEqualTo(32);
		assertThat(nicknamesProperty.getValue()).containsOnly("captain");
		
		
		nameProperty.setValue("hugo");
		ageProperty.setValue(33);
		nicknamesProperty.add("player");
		
		// still the old values
		assertThat(person.getName()).isEqualTo("horst");
		assertThat(person.getAge()).isEqualTo(32);
		assertThat(person.getNicknames()).containsOnly("captain");
		
		
		personWrapper.commit();
		
		// now the new values are reflected in the wrapped person
		assertThat(person.getName()).isEqualTo("hugo");
		assertThat(person.getAge()).isEqualTo(33);
		assertThat(person.getNicknames()).containsOnly("captain", "player");
		
		
		
		nameProperty.setValue("luise");
		ageProperty.setValue(15);
		nicknamesProperty.setValue(FXCollections.observableArrayList("student"));
		
		personWrapper.reset();
		
		assertThat(nameProperty.getValue()).isEqualTo(null);
		assertThat(ageProperty.getValue()).isEqualTo(0);
		assertThat(nicknamesProperty.getValue().size()).isEqualTo(0);
		
		// the wrapped object has still the values from the last commit.
		assertThat(person.getName()).isEqualTo("hugo");
		assertThat(person.getAge()).isEqualTo(33);
		assertThat(person.getNicknames()).containsOnly("captain", "player");
		
		
		personWrapper.reload();
		// now the properties have the values from the wrapped object
		assertThat(nameProperty.getValue()).isEqualTo("hugo");
		assertThat(ageProperty.getValue()).isEqualTo(33);
		assertThat(nicknamesProperty.get()).containsOnly("captain", "player");
		
		
		Person otherPerson = new Person();
		otherPerson.setName("gisela");
		otherPerson.setAge(23);
		otherPerson.setNicknames(Arrays.asList("referee"));
		
		personWrapper.set(otherPerson);
		personWrapper.reload();
		
		assertThat(nameProperty.getValue()).isEqualTo("gisela");
		assertThat(ageProperty.getValue()).isEqualTo(23);
		assertThat(nicknamesProperty.getValue()).containsOnly("referee");
		
		nameProperty.setValue("georg");
		ageProperty.setValue(24);
		nicknamesProperty.setValue(FXCollections.observableArrayList("spectator"));
		
		personWrapper.commit();
		
		// old person has still the old values
		assertThat(person.getName()).isEqualTo("hugo");
		assertThat(person.getAge()).isEqualTo(33);
		assertThat(person.getNicknames()).containsOnly("captain", "player");
		
		// new person has the new values
		assertThat(otherPerson.getName()).isEqualTo("georg");
		assertThat(otherPerson.getAge()).isEqualTo(24);
		assertThat(otherPerson.getNicknames()).containsOnly("spectator");
		
	}
	
	
	@Test
	public void testWithJavaFXPropertiesField() {
		PersonFX person = new PersonFX();
		person.setName("horst");
		person.setAge(32);
		person.setNicknames(Arrays.asList("captain"));
		
		ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);
		
		
		final StringProperty nameProperty = personWrapper.field(PersonFX::nameProperty);
		final IntegerProperty ageProperty = personWrapper.field(PersonFX::ageProperty);
		final ListProperty<String> nicknamesProperty = personWrapper.field(PersonFX::nicknamesProperty);
		
		assertThat(nameProperty.getValue()).isEqualTo("horst");
		assertThat(ageProperty.getValue()).isEqualTo(32);
		assertThat(nicknamesProperty.getValue()).containsOnly("captain");
		
		
		nameProperty.setValue("hugo");
		ageProperty.setValue(33);
		nicknamesProperty.add("player");
		
		// still the old values
		assertThat(person.getName()).isEqualTo("horst");
		assertThat(person.getAge()).isEqualTo(32);
		assertThat(person.getNicknames()).containsOnly("captain");
		
		
		personWrapper.commit();
		
		// now the new values are reflected in the wrapped person
		assertThat(person.getName()).isEqualTo("hugo");
		assertThat(person.getAge()).isEqualTo(33);
		assertThat(person.getNicknames()).containsOnly("captain", "player");
		
		
		
		nameProperty.setValue("luise");
		ageProperty.setValue(15);
		nicknamesProperty.setValue(FXCollections.observableArrayList("student"));
		
		personWrapper.reset();
		
		assertThat(nameProperty.getValue()).isEqualTo(null);
		assertThat(ageProperty.getValue()).isEqualTo(0);
		assertThat(nicknamesProperty.getValue()).isEmpty();
		
		// the wrapped object has still the values from the last commit.
		assertThat(person.getName()).isEqualTo("hugo");
		assertThat(person.getAge()).isEqualTo(33);
		assertThat(person.getNicknames()).containsOnly("captain", "player");
		
		
		personWrapper.reload();
		// now the properties have the values from the wrapped object
		assertThat(nameProperty.getValue()).isEqualTo("hugo");
		assertThat(ageProperty.getValue()).isEqualTo(33);
		assertThat(nicknamesProperty.get()).containsOnly("captain", "player");
		
		
		PersonFX otherPerson = new PersonFX();
		otherPerson.setName("gisela");
		otherPerson.setAge(23);
		otherPerson.setNicknames(Arrays.asList("referee"));
		
		personWrapper.set(otherPerson);
		personWrapper.reload();
		
		assertThat(nameProperty.getValue()).isEqualTo("gisela");
		assertThat(ageProperty.getValue()).isEqualTo(23);
		assertThat(nicknamesProperty.get()).containsOnly("referee");
		
		nameProperty.setValue("georg");
		ageProperty.setValue(24);
		nicknamesProperty.setValue(FXCollections.observableArrayList("spectator"));
		
		personWrapper.commit();
		
		// old person has still the old values
		assertThat(person.getName()).isEqualTo("hugo");
		assertThat(person.getAge()).isEqualTo(33);
		assertThat(person.getNicknames()).containsOnly("captain", "player");
		
		// new person has the new values
		assertThat(otherPerson.getName()).isEqualTo("georg");
		assertThat(otherPerson.getAge()).isEqualTo(24);
	}
	
	@Test
	public void testIdentifiedFields() {
		Person person = new Person();
		person.setName("horst");
		person.setAge(32);
		person.setNicknames(Arrays.asList("captain"));
		
		ModelWrapper<Person> personWrapper = new ModelWrapper<>();
		
		final StringProperty nameProperty = personWrapper.field("name", Person::getName, Person::setName);
		final IntegerProperty ageProperty = personWrapper.field("age", Person::getAge, Person::setAge);
		final ListProperty<String> nicknamesProperty = personWrapper.field("nicknames", Person::getNicknames,
				Person::setNicknames);
		
		
		final StringProperty nameProperty2 = personWrapper.field("name", Person::getName, Person::setName);
		final IntegerProperty ageProperty2 = personWrapper.field("age", Person::getAge, Person::setAge);
		final ListProperty<String> nicknamesProperty2 = personWrapper.field("nicknames", Person::getNicknames,
				Person::setNicknames);
		
		
		assertThat(nameProperty).isSameAs(nameProperty2);
		assertThat(ageProperty).isSameAs(ageProperty2);
		assertThat(nicknamesProperty).isSameAs(nicknamesProperty2);
	}


	@Test
	public void testDirtyFlag() {
		Person person = new Person();
		person.setName("horst");
		person.setAge(32);
		person.setNicknames(Arrays.asList("captain"));

		ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge);
        final ListProperty<String> nicknames = personWrapper.field(Person::getNicknames, Person::setNicknames);

        name.set("hugo");

        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDirty()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();


        nicknames.add("player");
        assertThat(personWrapper.isDirty()).isTrue();

		nicknames.remove("player");
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        name.set("hans");
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();


        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.set(FXCollections.observableArrayList("player"));
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

    }

    @Test
    public void testDirtyFlagWithFxProperties() {
        PersonFX person = new PersonFX();
        person.setName("horst");
        person.setAge(32);

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDirty()).isFalse();

        final StringProperty name = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty age = personWrapper.field(PersonFX::ageProperty);
		final ListProperty<String> nicknames = personWrapper.field(PersonFX::nicknamesProperty);

        name.set("hugo");

        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        age.set(33);
        assertThat(personWrapper.isDirty()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();


        nicknames.add("player");
        assertThat(personWrapper.isDirty()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDirty()).isTrue(); // dirty is still true

        personWrapper.commit();
        assertThat(personWrapper.isDirty()).isFalse();

        name.set("hans");
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();


        personWrapper.reload();
        assertThat(personWrapper.isDirty()).isFalse();

        nicknames.set(FXCollections.observableArrayList("player"));
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reset();
        assertThat(personWrapper.isDirty()).isTrue();

        personWrapper.reload();
		assertThat(personWrapper.isDirty()).isFalse();
    }

    @Test
    public void testDifferentFlag() {
        Person person = new Person();
        person.setName("horst");
        person.setAge(32);
		person.setNicknames(Arrays.asList("captain"));

        ModelWrapper<Person> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDifferent()).isFalse();

        final StringProperty name = personWrapper.field(Person::getName, Person::setName);
        final IntegerProperty age = personWrapper.field(Person::getAge, Person::setAge);
		final ListProperty<String> nicknames = personWrapper.field(Person::getNicknames, Person::setNicknames);


        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();


        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDifferent()).isFalse();


        nicknames.remove("captain");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("captain");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();


        name.setValue("hans");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();


        personWrapper.reset();
        assertThat(personWrapper.isDifferent()).isTrue();
    }

	@Test
	public void testDifferentFlagWithFxProperties() {
        PersonFX person = new PersonFX();
        person.setName("horst");
        person.setAge(32);
        person.setNicknames(Arrays.asList("captain"));

        ModelWrapper<PersonFX> personWrapper = new ModelWrapper<>(person);

        assertThat(personWrapper.isDifferent()).isFalse();

        final StringProperty name = personWrapper.field(PersonFX::nameProperty);
        final IntegerProperty age = personWrapper.field(PersonFX::ageProperty);
        final ListProperty<String> nicknames = personWrapper.field(PersonFX::nicknamesProperty);


        name.set("hugo");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.commit();
        assertThat(personWrapper.isDifferent()).isFalse();


        age.set(33);
        assertThat(personWrapper.isDifferent()).isTrue();

        age.set(32);
        assertThat(personWrapper.isDifferent()).isFalse();


        nicknames.remove("captain");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.add("captain");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.add("player");
        assertThat(personWrapper.isDifferent()).isTrue();

        nicknames.remove("player");
        assertThat(personWrapper.isDifferent()).isFalse();

        nicknames.setValue(FXCollections.observableArrayList("spectator"));
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();


        name.setValue("hans");
        assertThat(personWrapper.isDifferent()).isTrue();

        personWrapper.reload();
        assertThat(personWrapper.isDifferent()).isFalse();


        personWrapper.reset();
        assertThat(personWrapper.isDifferent()).isTrue();
	}
    
    @Test
    public void testChangedFields() {
        PersonFX person = new PersonFX();
        person.setName("colin");
        person.setNicknames(Arrays.asList("amos", "henry"));
        
        ModelWrapper<PersonFX> modelWrapper = new ModelWrapper<>();
        modelWrapper.set(person);
        
        final StringProperty name = modelWrapper.field("name", PersonFX::nameProperty);
        final ListProperty<String> nicknames = modelWrapper.field("nicknames", PersonFX::nicknamesProperty);
        
        assertThat(modelWrapper.getChangedFields()).isEmpty();
        
        name.set("charles");
        assertThat(modelWrapper.getChangedFields()).isNotEmpty();
        assertThat(modelWrapper.getChangedFields().get(0).getFieldName()).isEqualTo("name");
        assertThat(modelWrapper.getChangedFields().get(0).getProperty().getValue()).isEqualTo("charles");
    }



	
}
