//import java.util.ArrayList;
//public class Student {
//    public String name;
//    public String group;
//    public int id;
//    public ArrayList<Book> BorrowedBooks = new ArrayList<Book>();
//    public Student(String name, String group) {
//        this.name = name;
//        this.group = group;
//        this.id = 12345;
//    }
//    public void BorrowBook(Book book) {
//        BorrowedBooks.add(book);
//    }
//    public void ShowBorrowedBooks() {
//        for (int i = 0; i < BorrowedBooks.size(); ++i) {
//            System.out.print(i + 1);
//            System.out.print(':');
//            System.out.println(BorrowedBooks.get(i));
//        }
//    }
//    public void DeleteBorrowedBooks() {
//        BorrowedBooks.clear();
//    }
//    public void DeleteBorrowedBookByIndex(int x) {
//        BorrowedBooks.remove(x);
//    }
//    @Override
//    public String toString() {
//        return "name=" + name +
//                ", group=" + group +
//                ", id=" + id;
//    }
//    public String getGroup() {
//        return group;
//    }
//    public void setGroup(String group) {
//        this.group = group;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public int getId() {
//        return id;
//    }
//}