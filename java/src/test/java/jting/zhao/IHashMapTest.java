package jting.zhao;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by zhaojt on 2018/2/21.
 */
public class IHashMapTest extends ITest {


    class Person{
        String cardNo;
        String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(cardNo, person.cardNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cardNo);
        }
    }

    class IdCard{
        String cardNo;
        Date validTo;//有效期

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IdCard idCard = (IdCard) o;
            return Objects.equals(cardNo, idCard.cardNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cardNo);
        }
    }

    @Test
    public void t1(){

        Person p1 = new Person();
        p1.cardNo = "22040319920128391X";
        p1.name = "赵浚廷";

        IdCard idCard = new IdCard();
        idCard.cardNo = "22040319920128391X";

        System.out.println(p1);
        System.out.println(idCard);

        System.out.println(p1.equals(idCard));//false  类型

        HashMap<Person, String> person = new HashMap<Person, String>();
        int i = 0;
        person.put(p1, "" + i++);

        p1.cardNo = p1.cardNo + "_" + i;
        person.put(p1, "" + i++);
        p1.cardNo = p1.cardNo + "_" + i;
        person.put(p1, "" + i++);
        p1.cardNo = p1.cardNo + "_" + i;
        person.put(p1, "" + i++);

        System.out.println(person.size());

        person.remove(p1);

        System.out.println(person.size());


    }
}
