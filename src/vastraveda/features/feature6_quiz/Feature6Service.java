package vastraveda.features.feature6_quiz;

import java.util.*;

public class Feature6Service {

    public static class Question {
        public String question;
        public List<String> options;
        public String answer;

        public Question(String q, List<String> o, String a) {
            question = q;
            options = o;
            answer = a;
        }
    }

    public List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(new Question("Which fabric is known as the 'Fabric of Freedom'?",
                Arrays.asList("Silk", "Khadi", "Cotton", "Linen"), "Khadi"));

        list.add(new Question("Paithani saree belongs to which state?",
                Arrays.asList("Gujarat", "Maharashtra", "Punjab", "Kerala"), "Maharashtra"));

        list.add(new Question("Bandhani is a type of?",
                Arrays.asList("Weaving", "Dyeing", "Printing", "Knitting"), "Dyeing"));

        list.add(new Question("Kalamkari is known for?",
                Arrays.asList("Painting", "Weaving", "Dyeing", "Knitting"), "Painting"));

        list.add(new Question("Which garment is traditionally worn by men?",
                Arrays.asList("Lehenga", "Dhoti", "Saree", "Salwar"), "Dhoti"));

        list.add(new Question("Banarasi sarees are famous for?",
                Arrays.asList("Cotton", "Silk", "Wool", "Linen"), "Silk"));

        list.add(new Question("Which state is famous for Bandhani?",
                Arrays.asList("Rajasthan", "Kerala", "Bihar", "Assam"), "Rajasthan"));

        list.add(new Question("Khadi is made using?",
                Arrays.asList("Machines", "Hand-spinning", "Chemical process", "Synthetic fibers"),
                "Hand-spinning"));

        return list;
    }
}