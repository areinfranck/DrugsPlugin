package me.theminddroid.drugs.states;

public class DrugWithdrawalState {
    private final long withdrawalStartTick;

    public DrugWithdrawalState(long withdrawalStart) {
        this.withdrawalStartTick = withdrawalStart;
    }

    public long getWithdrawalStartTick() {
        return withdrawalStartTick;
    }

    public boolean withdrawalIsActive(long currentTick){
        return currentTick - withdrawalStartTick < 600;
    }
}