package com.javarush.task.task27.task2712.ad;

public class Advertisement implements Comparable{
    private Object content; // - видео
    private String name;    // - имя/название
    private long initialAmount; // - начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    private int hits;        // - количество оплаченных показов
    private int duration;    // - продолжительность в секундах

    private long amountPerOneDisplaying; // стоимость одного показа рекламного объявления в копейках

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public long getAmountPerSecondDisplaying() {
        return (long) (((double)amountPerOneDisplaying/duration)*1000);
    }

    public void revalidate(){
        if (hits <= 0) throw new NoVideoAvailableException();;
        hits--;
    }


    public int getHits() {
        return hits;
    }

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        if (hits!=0) this.amountPerOneDisplaying = initialAmount/hits;

    }

    @Override
    public int compareTo(Object o) {
        int result = Long.compare(((Advertisement)o).amountPerOneDisplaying,this.amountPerOneDisplaying);
        if (result != 0) return result;
        else return Long.compare(this.getAmountPerOneDisplaying(),((Advertisement) o).getAmountPerSecondDisplaying());
    }
}
