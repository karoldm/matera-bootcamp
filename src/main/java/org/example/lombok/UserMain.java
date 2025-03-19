package org.example.lombok;

public class UserMain {
    public static void main(String[] args) {
        User maria = new User();
        maria.setId(1);
        maria.setName("Maria");
        maria.setAge(22);

        System.out.println("id: " + maria.getId());
        System.out.println("nome: " + maria.getName());
        System.out.println("idade: " + maria.getAge());
        System.out.println("maria: " + maria);

        System.out.println();

        User julia = new User(2, "Julia", 25);

        System.out.println("id: " + julia.getId());
        System.out.println("nome: " + julia.getName());
        System.out.println("idade: " + julia.getAge());
        System.out.println("julia: " + julia);
    }
}
