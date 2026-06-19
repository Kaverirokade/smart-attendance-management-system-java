package LineChart;

public class ModelData {
    private String dayLabel;              // e.g., "Mon"
    private double attendancePercent;     // e.g., 87.5

    public ModelData(String dayLabel, double attendancePercent) {
        this.dayLabel = dayLabel;
        this.attendancePercent = attendancePercent;
    }

    public String getDayLabel() {
        return dayLabel;
    }

    public void setDayLabel(String dayLabel) {
        this.dayLabel = dayLabel;
    }

    public double getAttendancePercent() {
        return attendancePercent;
    }

    public void setAttendancePercent(double attendancePercent) {
        this.attendancePercent = attendancePercent;
    }
}
