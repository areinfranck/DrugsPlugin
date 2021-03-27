package me.theminddroid.drugs.states;

import org.bukkit.scheduler.BukkitTask;

public class DrugUsageState {
    private final int amountOfTimesUsed;
    private final BukkitTask withdrawalTask;

    public DrugUsageState(int amountOfTimesUsed, BukkitTask WithdrawalTask) {
        this.amountOfTimesUsed = amountOfTimesUsed;
        this.withdrawalTask = WithdrawalTask;
    }

    public int getAmountOfTimesUsed() {
        return amountOfTimesUsed;
    }

    public BukkitTask getWithdrawalTask() {
        return withdrawalTask;
    }
}