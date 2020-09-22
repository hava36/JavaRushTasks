package com.javarush.task.task39.task3913;
import com.javarush.task.task39.task3913.query.*;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private List<LogEntry> entries = new ArrayList<>();
    private File logDir;
    private List<File> logs = new ArrayList<>();
    private void processFilesFromFolder(List<File> logs, File file) {
        File[] folderEntries = file.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                processFilesFromFolder(logs, entry);
            }
            if (entry.isFile() && entry.getName().endsWith(".log")) {
                logs.add(entry);
            }
        }
    }
    private void getEntries(){
        processFilesFromFolder(logs, logDir);
        for(File file : logs){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String input = "";
                while ((input = reader.readLine()) != null){
                    entries.add(new LogEntry(input));
                }
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public LogParser(Path logDir) {
        this.logDir = new File(logDir.toString());
        getEntries();
    }
    private List<LogEntry> getFilteredLogEntries(Date after, Date before){
        List<LogEntry> logs = new ArrayList<>();
        for (LogEntry entry : entries){
            if (isDateInRange(entry.getDateOfEvent(), after, before)){
                logs.add(entry);
            }
        }
        return logs;
    }
    private boolean isDateInRange(Date check, Date after, Date before) {
        boolean fits = before == null || check.before(before) || check.equals(before);
        return fits && (after == null || check.after(after) || check.equals(after));
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> ips = new HashSet<>();
        List<LogEntry> entries = getFilteredLogEntries(after, before);
        for (LogEntry entry : entries){
            ips.add(entry.getIp());
        }
        return ips;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        List<LogEntry> entries = getFilteredLogEntries(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (user.equals(entry.getName())){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        List<LogEntry> entries = getFilteredLogEntries(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (event == entry.getEvent()){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        List<LogEntry> entries = getFilteredLogEntries(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (status == entry.getStatus()){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> names = new HashSet<>();
        for (LogEntry entry : entries){
            names.add(entry.getName());
        }
        return names;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for (LogEntry entry : filtered){
            names.add(entry.getName());
        }
        return names.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if(entry.getName().equals(user)){
                events.add(entry.getEvent());
            }
        }
        return events.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getIp().equals(ip)){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.LOGIN){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.DOWNLOAD_PLUGIN && entry.getStatus() == Status.OK){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.WRITE_MESSAGE && entry.getStatus() == Status.OK){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.SOLVE_TASK){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.SOLVE_TASK && entry.getEventNumber() == task){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.DONE_TASK){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<String> names = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getEvent() == Event.DONE_TASK && entry.getEventNumber() == task){
                names.add(entry.getName());
            }
        }
        return names;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Date> dates = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getName().equals(user) && entry.getEvent() == event){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Date> dates = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.status == Status.FAILED){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Date> dates = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.status == Status.ERROR){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        List<Date> dates = new ArrayList<>();
        for(LogEntry entry : filtered){
            if (entry.getName().equals(user) && entry.getEvent() == Event.LOGIN){
                dates.add(entry.getDateOfEvent());
            }
        }
        Collections.sort(dates);
        if (!dates.isEmpty()) {
            return dates.get(0);
        }
        return null;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        List<Date> dates = new ArrayList<>();
        for(LogEntry entry : filtered){
            if (entry.getName().equals(user) && entry.getEvent() == Event.SOLVE_TASK && entry.getEventNumber() == task){
                dates.add(entry.getDateOfEvent());
            }
        }
        Collections.sort(dates);
        if (!dates.isEmpty()) {
            return dates.get(0);
        }
        return null;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        List<Date> dates = new ArrayList<>();
        for(LogEntry entry : filtered){
            if (entry.getName().equals(user) && entry.getEvent() == Event.DONE_TASK && entry.getEventNumber() == task){
                dates.add(entry.getDateOfEvent());
            }
        }
        Collections.sort(dates);
        if (!dates.isEmpty()) {
            return dates.get(0);
        }
        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Date> dates = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getName().equals(user) && entry.getEvent() == Event.WRITE_MESSAGE){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Date> dates = new HashSet<>();
        for(LogEntry entry : filtered){
            if (entry.getName().equals(user) && entry.getEvent() == Event.DOWNLOAD_PLUGIN){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            events.add(entry.getEvent());
        }
        return events;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getIp().equals(ip)){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getName().equals(user)){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getStatus() == Status.FAILED){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getStatus() == Status.ERROR){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        int attempts = 0;
        for (LogEntry entry : filtered){
            if (entry.getEvent() == Event.SOLVE_TASK && entry.getEventNumber() == task){
                attempts++;
            }
        }
        return attempts;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        int attempts = 0;
        for (LogEntry entry : filtered){
            if (entry.getEvent() == Event.DONE_TASK && entry.getEventNumber() == task ){
                attempts++;
            }
        }
        return attempts;
    }

    private Set<Integer> getUniqueTasksSolved(Date after, Date before){
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Integer> uTasks = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getEvent() == Event.SOLVE_TASK){
                uTasks.add(entry.getEventNumber());
            }
        }
        return uTasks;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> tasks = getUniqueTasksSolved(after, before);
        for (Integer task : tasks){
            map.put(task, getNumberOfAttemptToSolveTask(task, after, before));
        }
        return map;
    }

    private Set<Integer> getUniqueTasksDone(Date after, Date before){
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        Set<Integer> uTasks = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getEvent() == Event.DONE_TASK){
                uTasks.add(entry.getEventNumber());
            }
        }
        return uTasks;
    }

    private int getNumberOfAttemptToDoneTask(int task, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntries(after, before);
        int attempts = 0;
        for (LogEntry entry : filtered){
            if (entry.getEvent() == Event.DONE_TASK && entry.getEventNumber() == task){
                attempts++;
            }
        }
        return attempts;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> tasks = getUniqueTasksDone(after, before);
        for (Integer task : tasks){
            map.put(task, getNumberOfAttemptToDoneTask(task, after, before));
        }
        return map;
    }

    @Override
    public Set<Object> execute(String query) {
        switch (query) {
            case "get ip":
                return (Set<Object>)(Collection<?>)getUniqueIPs(null, null);
            case "get user":
                return (Set<Object>)(Collection<?>)getAllUsers();
            case "get date":{
                Set<Date> dates = new HashSet<>();
                for(LogEntry entry : entries){
                    dates.add(entry.getDateOfEvent());
                }
                return (Set<Object>)(Collection<?>)dates;
            }
            case "get event":
                return (Set<Object>)(Collection<?>)getAllEvents(null, null);
            case "get status":{
                Set<Status> statuses = new HashSet<>();
                for(LogEntry entry : entries){
                    statuses.add(entry.getStatus());
                }
                return (Set<Object>)(Collection<?>)statuses;
            }
        }
        Pattern queryPattern = Pattern.compile("get\\s+(?<field1>\\w+)\\s+\\w+\\s+(?<field2>\\w+)\\s+=\\s+\"(?<value>\\w+|[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+|[\\d]+.[\\d]+.[\\d]+.[\\d]+|[a-zA-Z ]+)\"(\\s{0,}$|\\s+\\w+\\s+\\w+\\s+\\w+\\s+\"(?<after>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\"\\s+\\w+\\s+\"(?<before>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\")");
        Matcher matcher = queryPattern.matcher(query);
        matcher.find();
        String field1 = matcher.group("field1");
        String field2 = matcher.group("field2");
        String value = matcher.group("value");

        Date after = null;
        Date before = null;
        try {
            String afterStr = matcher.group("after");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            try {
                if (afterStr != null) after = simpleDateFormat.parse(afterStr);
            } catch (ParseException e){
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e){
            after = null;
        }
        try {
            String beforeStr = matcher.group("before");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            try {
                if (beforeStr != null) before = simpleDateFormat.parse(beforeStr);
            } catch (ParseException e){
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e){
            after = null;
        }


        if (field1.equals("ip") && field2.equals("user")){
            return (Set<Object>)(Collection<?>)getIPsForUserStrong(value, after, before);
        }
        if (field1.equals("ip") && field2.equals("date")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            Date valueDate = null;
            try {
                valueDate = simpleDateFormat.parse(value);
            } catch (ParseException e){
                e.printStackTrace();
            }
            return (Set<Object>)(Collection<?>)getIPsForDate(valueDate, after, before);
        }
        if (field1.equals("ip") && field2.equals("event")){
            return (Set<Object>)(Collection<?>)getIPsForEventStrong(Event.valueOf(value.toUpperCase()), after, before);
        }
        if (field1.equals("ip") && field2.equals("status")){
            return (Set<Object>)(Collection<?>)getIPsForStatusStrong(Status.valueOf(value.toUpperCase()), after, before);
        }
        if (field1.equals("user") && field2.equals("ip")){
            return (Set<Object>)(Collection<?>)getUsersForIPStrong(value, after, before);
        }
        if (field1.equals("user") && field2.equals("date")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            Date valueDate = null;
            try {
                valueDate = simpleDateFormat.parse(value);
            } catch (ParseException e){
                e.printStackTrace();
            }
            return (Set<Object>)(Collection<?>)getUsersForDate(valueDate, after, before);
        }
        if (field1.equals("user") && field2.equals("event")){
            return (Set<Object>)(Collection<?>)getUsersForEvent(Event.valueOf(value), after, before);
        }
        if (field1.equals("user") && field2.equals("status")){
            return (Set<Object>)(Collection<?>)getUsersForStatus(Status.valueOf(value), after, before);
        }
        if (field1.equals("date") && field2.equals("ip")){
            return (Set<Object>)(Collection<?>)getDatesForIp(value, after, before);
        }
        if (field1.equals("date") && field2.equals("user")){
            return (Set<Object>)(Collection<?>)getDatesForUser(value, after, before);
        }
        if (field1.equals("date") && field2.equals("event")){
            return (Set<Object>)(Collection<?>)getDatesForEvent(Event.valueOf(value.toUpperCase()), after, before);
        }
        if (field1.equals("date") && field2.equals("status")){
            return (Set<Object>)(Collection<?>)getDatesForStatus(Status.valueOf(value), after, before);
        }
        if (field1.equals("event") && field2.equals("ip")){
            return (Set<Object>)(Collection<?>)getEventsForIPStrong(value, after, before);
        }
        if (field1.equals("event") && field2.equals("user")){
            return (Set<Object>)(Collection<?>)getEventsForUserStrong(value, after, before);
        }
        if (field1.equals("event") && field2.equals("date")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            Date valueDate = null;
            try {
                valueDate = simpleDateFormat.parse(value);
            } catch (ParseException e){
                e.printStackTrace();
            }
            return (Set<Object>)(Collection<?>)getEventsForDate(valueDate, after, before);
        }
        if (field1.equals("event") && field2.equals("status")){
            return (Set<Object>)(Collection<?>)getEventsForStatus(Status.valueOf(value), after, before);
        }
        if (field1.equals("status") && field2.equals("ip")){
            return (Set<Object>)(Collection<?>)getStatusForIp(value, after, before);
        }
        if (field1.equals("status") && field2.equals("user")){
            return (Set<Object>)(Collection<?>)getStatusForUser(value, after, before);
        }
        if (field1.equals("status") && field2.equals("date")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            Date valueDate = null;
            try {
                valueDate = simpleDateFormat.parse(value);
            } catch (ParseException e){
                e.printStackTrace();
            }
            return (Set<Object>)(Collection<?>)getStatusForDate(valueDate, after, before);
        }
        if (field1.equals("status") && field2.equals("event")){
            return (Set<Object>)(Collection<?>)getStatusForEvent(Event.valueOf(value), after, before);
        }

        return null;
    }

    private Set<Status> getStatusForEvent(Event event, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Status> status = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getEvent() == event){
                status.add(entry.getStatus());
            }
        }
        return status;
    }

    private Set<Status> getStatusForDate(Date date, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Status> status = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getDateOfEvent().getTime() == date.getTime()){
                status.add(entry.getStatus());
            }
        }
        return status;
    }

    private Set<Status> getStatusForUser(String user, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Status> status = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getName().equals(user)){
                status.add(entry.getStatus());
            }
        }
        return status;
    }

    private Set<Status> getStatusForIp(String ip, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Status> status = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getIp().equals(ip)){
                status.add(entry.getStatus());
            }
        }
        return status;
    }

    private Set<Event> getEventsForStatus(Status status, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getStatus() == status){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    private Set<Event> getEventsForDate(Date date, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getDateOfEvent().getTime() == date.getTime()){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    private Set<Date> getDatesForStatus(Status status, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Date> dates = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getStatus() == status){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    private Set<Date> getDatesForEvent(Event event, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Date> dates = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getEvent() == event){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    private Set<Date> getDatesForUser(String name, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Date> dates = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getName().equals(name)){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    private Set<Date> getDatesForIp(String ip, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<Date> dates = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getIp().equals(ip)){
                dates.add(entry.getDateOfEvent());
            }
        }
        return dates;
    }

    private Set<String> getUsersForStatus(Status status, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> users = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getStatus() == status){
                users.add(entry.getName());
            }
        }
        return users;
    }

    private Set<String> getUsersForEvent(Event event, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> users = new HashSet<>();
        for (LogEntry entry : entries){
            if (entry.getEvent() == event){
                users.add(entry.getName());
            }
        }
        return users;
    }

    private Set<String> getUsersForDate(Date date, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> users = new HashSet<>();
        for (LogEntry entry : entries){
            if (date.getTime() == entry.getDateOfEvent().getTime()){
                users.add(entry.getName());
            }
        }
        return users;
    }

    private Set<String> getIPsForDate(Date date, Date after, Date before){
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (date.getTime() == entry.getDateOfEvent().getTime()){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    private Set<Event> getEventsForUserStrong(String user, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntriesStrong(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getName().equals(user)){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    private Set<Event> getEventsForIPStrong(String ip, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntriesStrong(after, before);
        Set<Event> events = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getIp().equals(ip)){
                events.add(entry.getEvent());
            }
        }
        return events;
    }

    private Set<String> getUsersForIPStrong(String ip, Date after, Date before) {
        List<LogEntry> filtered = getFilteredLogEntriesStrong(after, before);
        Set<String> names = new HashSet<>();
        for (LogEntry entry : filtered){
            if (entry.getIp().equals(ip)){
                names.add(entry.getName());
            }
        }
        return names;
    }

    private Set<String> getIPsForStatusStrong(Status status, Date after, Date before) {
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (status == entry.getStatus()){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    private Set<String> getIPsForEventStrong(Event event, Date after, Date before) {
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (event == entry.getEvent()){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    private Set<String> getIPsForUserStrong(String user, Date after, Date before) {
        List<LogEntry> entries = getFilteredLogEntriesStrong(after, before);
        Set<String> ips = new HashSet<>();
        for (LogEntry entry : entries){
            if (user.equals(entry.getName())){
                ips.add(entry.getIp());
            }
        }
        return ips;
    }

    private List<LogEntry> getFilteredLogEntriesStrong(Date after, Date before){
        List<LogEntry> logs = new ArrayList<>();
        for (LogEntry entry : entries){
            if (isDateInRangeStrong(entry.getDateOfEvent(), after, before)){
                logs.add(entry);
            }
        }
        return logs;
    }
    private boolean isDateInRangeStrong(Date check, Date after, Date before) {
        boolean fits = before == null || check.before(before);
        return fits && (after == null || check.after(after));
    }

    private class LogEntry {
        private String ip;
        private String name;
        private Date dateOfEvent;
        private Event event;
        private Integer eventNumber = null;
        private Status status;
        public LogEntry(String log){
            parseIP(log);
            parseName(log);
            parseDate(log);
            parseEvent(log);
            parseStatus(log);
        }
        private Pattern pattern = Pattern.compile("(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s" +
                "(?<name>[a-zA-Z ]+)\\s" +
                "(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s" +
                "(?<event>[\\w]+)\\s?(" +
                "(?<taskNumber>[\\d]+)|)\\s" +
                "(?<status>[\\w]+)");
        private void parseIP(String log){
            Matcher matcher = pattern.matcher(log);
            matcher.find();
            ip = matcher.group("ip");
        }
        private void parseName(String log){
            Matcher matcher = pattern.matcher(log);
            matcher.find();
            name = matcher.group("name");
        }
        private void parseDate(String log){
            Matcher matcher = pattern.matcher(log);
            matcher.find();
            String sDate = matcher.group("date");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
            try{
                dateOfEvent = simpleDateFormat.parse(sDate);
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        private void parseEvent(String log){
            Matcher matcher = pattern.matcher(log);
            matcher.find();
            event = Event.valueOf(matcher.group("event"));
            if ((event == Event.SOLVE_TASK) || (event == Event.DONE_TASK)){
                eventNumber = Integer.parseInt(matcher.group("taskNumber"));
            }
        }
        private void parseStatus(String log){
            Matcher matcher = pattern.matcher(log);
            matcher.find();
            status = Status.valueOf(matcher.group("status"));
        }

        public String getIp() {
            return ip;
        }

        public String getName() {
            return name;
        }

        public Date getDateOfEvent() {
            return dateOfEvent;
        }

        public Event getEvent() {
            return event;
        }

        public Integer getEventNumber() {
            return eventNumber;
        }

        public Status getStatus() {
            return status;
        }
    }
}