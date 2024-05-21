
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class SubwayMap {
    private Map<String, Map<String, Double>> map;


    public SubwayMap() {
        this.map = new LinkedHashMap<>();
    }//构造函数

    public void addLine(String lineName) {
        map.put(lineName, new LinkedHashMap<>());
    }

    public void addStation(String lineName, String stationName, double distance) {
        map.get(lineName).put(stationName, distance);
    }
    //上面的方法构建了地铁网络并输出

    public Set<String> getTransferStations() {
        Map<String, Set<String>> stationLines = new HashMap<>();
        for (String line : map.keySet()) {
            for (String station : map.get(line).keySet()) {
                stationLines.putIfAbsent(station, new HashSet<>());
                stationLines.get(station).add(line);
            }
        }

        Set<String> transferStations = new HashSet<>();
        for (String station : stationLines.keySet()) {
            if (stationLines.get(station).size() > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("<").append(station).append(", <");
                for (String line : stationLines.get(station)) {
                    sb.append(line).append(" 号线、");
                }
                sb.setLength(sb.length() - 1); //去掉最后的逗号
                sb.append(">>");
                transferStations.add(sb.toString());
            }
        }

        return transferStations;
    }//中转站获取


    public String toString() {
        return this.map.values().toString();
    }//调整格式，利于输出

    public List<String> findStationsWithinDistance(String station, int distance) {
        List<String> Line = new ArrayList<>();
        int t = 0;
        List<String> results = new ArrayList<>();
        // 遍历每一条地铁线路
        for (String i : map.keySet()) {
            Line.add(i);
        }
        for (Map<String, Double> M : map.values()) {
            // 检查输入的站点是否在当前线路中

            String line = Line.get(t);
            ArrayList<String> stations = new ArrayList<String>();
            for (String i : M.keySet()) {
                stations.add(i);
            }

            if (stations.contains(station)) {
                int index = stations.indexOf(station);
                // 在站点前后遍历以找到与输入站点相隔 `n` 站以内的所有站点
                for (int i = Math.max(0, index - distance); i <= Math.min(stations.size() - 1, index + distance); i++) {
                    // 计算相隔的站点数量
                    int distanceFromStation = Math.abs(i - index);
                    // 构建结果字符串并添加到结果列表中

                    results.add("<<" + stations.get(i) + "," + line + "号线" + "," + distanceFromStation + ">>");
                }
            }
            t = t + 1;
        }
        return results;
    }

    private List<String> ConnectedStations(String station) {
        List<String> connectedStations = new ArrayList<>();

        for (Map<String, Double> M : map.values()) {
            // 检查输入的站点是否在当前线路中

            ArrayList<String> stations = new ArrayList<String>();
            for (String i : M.keySet()) {
                stations.add(i);
            }


            if (stations.contains(station)) {
                int index = stations.indexOf(station);
                if (index > 0) {
                    connectedStations.add(stations.get(index - 1)); // 添加前一个站点
                }
                if (index < stations.size() - 1) {
                    connectedStations.add(stations.get(index + 1)); // 添加后一个站点
                }
            }
        }
        return connectedStations;
    }//辅助函数获取相连站点

    private void DFS(String currentStation, String endStation, Set<String> visited, List<String> currentPath, List<List<String>> allPaths) {
        visited.add(currentStation);
        // 如果当前站点是终点站，则将当前路径添加到结果集中
        if (currentStation.equals(endStation)) {
            allPaths.add(new ArrayList<>(currentPath));
            System.out.println(currentPath);
        } else {
            // 否则，遍历当前站点相连的所有站点
            List<String> connectedStations = ConnectedStations(currentStation);
            for (String nextStation : connectedStations) {
                if (!visited.contains(nextStation)) {
                    currentPath.add(nextStation);
                    DFS(nextStation, endStation, visited, currentPath, allPaths);
                    currentPath.remove(currentPath.size() - 1); // 逆向回溯
                }
            }
        }
        visited.remove(currentStation);
    }

    public ArrayList<List<String>> findAllPaths(String startStation, String endStation) {
        ArrayList<List<String>> allPaths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        currentPath.add(startStation);
        DFS(startStation, endStation, visited, currentPath, allPaths);
        return allPaths;
    }//问题（3）获取所有的路线

    public double Connnecteddistance (String s1, String s2){
        Double distance = (double) 0;
        for (Map<String, Double> M : map.values()) {
            // 检查输入的站点是否在当前线路中

            ArrayList<Double> distances = new ArrayList<Double>();
            ArrayList<String> stations = new ArrayList<String>();
            for (String i : M.keySet()) {
                stations.add(i);
            }
            for (Double d : M.values()) {
                distances.add(d);
            }
            if ((stations.contains(s1)) && (stations.contains(s2))) {
                int index1 = stations.indexOf(s1);
                int index2 = stations.indexOf(s2);
                int q = index1 - index2;
                if (q == 1) {
                    distance = distances.get(index2);
                } else {
                    distance = distances.get(index1);


                }
            }
        }
        return distance;
    }

    public Double getpathdistance (ArrayList < String > list) {
        Double d = (double) 0;
        for (int i = 0; i < list.toArray().length - 1; i++) {
            d = d + this.Connnecteddistance(list.get(i), list.get(i + 1));
        }
        return d;
    }

    public Double shortestdistance (ArrayList < Double > list) {
        double d = list.get(0);
        for (int i = 0; i <= list.toArray().length - 1; i++) {
            double t = list.get(i);
            if (d > t) {
                d = t;
            }
        }
        return d;
    }

    public String getline (String s1, String s2){
        String nowline = null;
        ArrayList<String> lines = new ArrayList<String>();
        for (String i : map.keySet()) {
            lines.add(i);
        }
        int i = 0;

        for (Map<String, Double> M : map.values()) {


            String line = lines.get(i);
            ArrayList<String> stations = new ArrayList<String>();
            for (String s : M.keySet()) {
                stations.add(s);
            }

            if ((stations.contains(s1)) && (stations.contains(s2))) {
                nowline = line;
            }

            i++;
        }
        return nowline;

    }

    public void functionalTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入起点站名称：");
        String startStation = scanner.nextLine();
        System.out.print("请输入终点站名称：");
        String endStation = scanner.nextLine();

        List<List<String>> allPaths = this.findAllPaths(startStation, endStation);
        if (allPaths.isEmpty()) {
            System.out.println("未找到起点站和终点站之间的路径。");//不合规输入的异常处理
        } else {
            System.out.println(startStation + "到" + endStation + "的路径如上");
        }
        ArrayList<Double> distances = new ArrayList<Double>();
        for (List<String> path : allPaths) {
            distances.add(this.getpathdistance((ArrayList<String>) path));
        }
        double shortestdistance = this.shortestdistance(distances);
        int indix = distances.indexOf(shortestdistance);
        ArrayList<String> shortestestpath = (ArrayList<String>) allPaths.get(indix);
        System.out.println("最短路径为：");
        System.out.println(shortestestpath);
        System.out.println("最短距离为");
        System.out.println(shortestdistance);
        System.out.print("请输入你选择的路线：");
        int ln = scanner.nextInt();
        this.showPath((ArrayList<String>) allPaths.get(ln));

        System.out.println("距离为" + distances.get(ln));
        System.out.println("请选择你的支付方式");
        String payway = scanner.next();
        if (payway.equals("单程票")) {
            System.out.println("您选择单程票支付");
            onewayTicket onewayticket = new onewayTicket();
            System.out.println("价格为" + onewayticket.getfare(distances.get(ln)));
        }
        if (payway.equals("武汉通")) {
            System.out.println("您选择武汉通支付");
            wuhanMetrotong wuhantong = new wuhanMetrotong();
            System.out.println("价格为" + wuhantong.getfare(distances.get(ln)));
        }
        if (payway.equals("定期票")) {
            System.out.println("您选择定期票支付");
            System.out.println("价格为" + 0);
        }
        scanner.close();
    }//通过上面几个方法的辅助，能够实现所有功能的测试


    public void showPath (ArrayList < String > path) {
        String firststation = path.get(0);
        String laststation = path.get(path.toArray().length - 1);
        String firstline = this.getline(path.get(0), path.get(1));
        System.out.println("从" + firststation + "出发" + "沿" + firstline + "号线");
        for (int i = 1; i < path.toArray().length - 1; i++) {
            String previouspath = this.getline(path.get(i - 1), path.get(i));
            String nowpath = this.getline(path.get(i), path.get(i + 1));
            if (!previouspath.equals(nowpath)) {
                System.out.println("到" + path.get(i));
                System.out.println("转乘" + nowpath + "号线");
            }
        }
        System.out.println("到" + laststation);
    }//问题（5）,乘车方法的展示

}



















