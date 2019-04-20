package tools;

public class main {

    public static void main(String[] arg) {

        String temp = "\"AL\",\"AK\",\"AZ\",\"AR\",\"CA\",\"CO\",\"CT\",\"DE\",\"FL\",\"GA\",\"HI\",\"ID\",\"IL\",\n" +
                "\"IN\",\"IA\",\"KS\",\"KY\",\"LA\",\"ME\",\"MD\",\"MA\",\"MI\",\"MN\",\"MO\",\"MT\",\"NE\",\"NV\",\n" +
                "\"N\",'NJ','NM','NY','NC','ND','OH','OK','OR','PA','RI','SC','SD','TD',\n" +
                "'TX','UT','VT','VA','WA','WV','WI','WY','Other'";

        System.out.println(temp);

        temp.replaceAll("'","\"");
        System.out.println(temp);
    }
}
