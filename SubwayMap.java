import java.util.*;

public class SubwayMap {
    private Map<String, Map<String, Double>> map;


    public SubwayMap() {
        this.map = new LinkedHashMap<>();
    }


    public void addLine(String lineName) {
        map.put(lineName, new LinkedHashMap<>());
    }

    public void addStation(String lineName, String stationName, double distance) {
        map.get(lineName).put(stationName, distance);
    }

    //第一问：获取所有中转站
    public Set<String> getTransferStations() {
        Map<String, Set<String>> stationLines = new HashMap<>();
        for (String line : map.keySet()) {
            for (String station : map.get(line).keySet()) {
                stationLines.putIfAbsent(station, new HashSet<>());
                stationLines.get(station).add(line);
            }
        }//找出路线上的交集元素

        Set<String> transferStations = new HashSet<>();
        for (String station : stationLines.keySet()) {
            if (stationLines.get(station).size() > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("<").append(station).append(", <");
                for (String line : stationLines.get(station)) {
                    sb.append(line).append(" 号线、");
                }
                sb.setLength(sb.length() - 1); // 删除最后的逗号
                sb.append(">>");
                transferStations.add(sb.toString());
            }
        }

        return transferStations;
    }

    //图转字符串形式便于输出
    public String toString() {
        return this.map.values().toString();
    }

    //第二问：输入某一站点，输出线路距离小于 n 的所有站点集合
    public List<String> findStationsWithinDistance(String station, int distance) {
        List<String> results = new ArrayList<>();        
        List<String> Line = new ArrayList<>();
        int t = 0;
        for (String i : map.keySet()) {
            Line.add(i);
        }// 遍历每一条地铁线路
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
                    results.add("<<" + stations.get(i) + "," + line + "号线" + "," + distanceFromStation + ">>");
                    //按题目要求表达
                }
            }
            t = t + 1;
        }
        return results;
    }

    //辅助函数，获取相连接的站点集合
    private List<String> getConnectedstations(String station) {
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
    }

    //深度优先算法遍历求解最优路线
    private void DFSpath(String currentStation, String endStation, Set<String> visited, List<String> currentPath, List<List<String>> allPaths) {
        visited.add(currentStation);
        // 如果当前站点是终点站，则将当前路径添加到返回集合中
        if (currentStation.equals(endStation)) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            // 否则遍历当前站点相连的所有站点
            List<String> connectedStations = getConnectedstations(currentStation);
            for (String nextStation : connectedStations) {
                if (!visited.contains(nextStation)) {
                    currentPath.add(nextStation);
                    DFSpath(nextStation, endStation, visited, currentPath, allPaths);
                    currentPath.remove(currentPath.size() - 1); // 回溯
                }
            }
        }
        visited.remove(currentStation);
    }
    
    //第三问，获取某起点站到达某终点站的所有路径
    public ArrayList<List<String>> findAllPaths(String startStation, String endStation) {
        ArrayList<List<String>> allPaths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        currentPath.add(startStation);
        DFSpath(startStation, endStation, visited, currentPath, allPaths);
        return allPaths;
    }

    //异常处理，对于不合规的操作的处理
    public void exceptionHand(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入起点站名称：");
        String startStation = scanner.nextLine();
        System.out.print("请输入终点站名称：");
        String endStation = scanner.nextLine();

        List<List<String>> allPaths = this.findAllPaths(startStation, endStation);
        if (allPaths.isEmpty()) {
            System.out.println("未找到起点站和终点站之间的路径。");
        }
        scanner.close();
    }

}
