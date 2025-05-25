package queries;

import entities.Grade;

import java.util.ArrayList;
import java.util.List;

public class GradeDetail {
    //选课信息
    private int takesId;

    // 成绩组成部分列表
    private List<ScoreComponent> components = new ArrayList<>();

    // 构造方法
    public GradeDetail(ArrayList<Grade> grades) {
        this.takesId = grades.get(0).getTakeId();
        for(Grade grade : grades){
            components.add(new ScoreComponent(grade.getName(),grade.getGrade(),grade.getProportion(),grade.getGradeType()));
        }
    }

    public GradeDetail(List<Grade> grades) {
        if (!grades.isEmpty()) {
            this.takesId = grades.get(0).getTakeId();
            for (Grade grade : grades) {
                components.add(new ScoreComponent(
                    grade.getName(),
                    grade.getGrade(),
                    grade.getProportion(),
                    grade.getGradeType()
                ));
            }
        }
    }

    // 核心方法：计算总成绩
    public double calculateTotalScore() {
        double total = 0.0;
        for (ScoreComponent component : components) {
            total += component.getContribution();
        }
        return total;
    }

    // 内部类：成绩组成部分
    public static class ScoreComponent {
        private String name;        // 组成部分名称（如"期末考试"）
        private double score;        // 实际得分
        private double proportion;       // 占总成绩权重（如0.3表示30%）
        private String type;

        //总分计算
        public ScoreComponent(String name, double score, double proportion, String type) {
            this.name = name;
            this.score = score;
            this.proportion = proportion;
            this.type = type;
        }

        public double getContribution() {
            return score * proportion;
        }


        public String getName() { return name; }
        public double getScore() { return score; }
        public double getProportion() { return proportion; }
        public void setName(String name) { this.name = name;}
        public void setScore(double score) { this.score = score;}
        public void setProportion(double proportion) { this.proportion = proportion;}
    }

    public int getTakesId() { return takesId; }
    public List<ScoreComponent> getComponents() { return components; }

}