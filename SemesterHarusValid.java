package mod13_5190411485;

public class SemesterHarusValid extends Exception{

    @Override
    public String getMessage() {
        return "Inputan semester di luar range masa studi";
    }
}
