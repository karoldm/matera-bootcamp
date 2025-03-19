package org.example.equals;

public class EqualsMain {
    public static void main(String[] args) {
        String name1 = "Maria";
        String name2 = "Maria";
        String name3 = new String("Maria");

        System.out.println(name1 == name2); // true
        /*
          Comparação com == (Comparação de Referência)
             name1 == name2: Retorna true porque ambos fazem referência à mesma instância na String Pool do Java.
             name1 == name3: Retorna false porque name3 foi criado com new String("Maria"), o que força a criação de um novo objeto na memória heap, diferente da string armazenada na String Pool.

          Comparação com .equals() (Comparação de Conteúdo)
             name1.equals(name2): Retorna true porque ambos possuem o mesmo conteúdo, mesmo que a verificação com == também fosse verdadeira.
             name1.equals(name3): Retorna true porque .equals() compara o conteúdo da string, e não a referência na memória.
        */
        System.out.println(name1 == name3); // false
        System.out.println(name1.equals(name2)); // true
        System.out.println(name1.equals(name3)); // true

        name2 = "maria";
        System.out.println(name1 == name2); // false
        System.out.println(name1.equalsIgnoreCase(name2)); // true
    }
}
