package block3.cp.mapreduce;

import java.util.ArrayList;
import java.util.List;

public class MapReduceImpl extends MapReduceBase<Employee, Integer, Double> {
    public List<Integer> list =new ArrayList<>();

    @Override
    protected Integer map(Employee employee) {
        return employee.getAge();
    }

    @Override
    protected Double reduce(List<Integer> list) {
        double sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum/list.size();
    }

}