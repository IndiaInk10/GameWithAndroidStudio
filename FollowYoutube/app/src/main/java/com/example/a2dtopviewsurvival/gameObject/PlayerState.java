package com.example.a2dtopviewsurvival.gameObject;

public class PlayerState {
    public enum State {
        IDLE,
        WALK,
    }

    private Player player;
    private State state;

    public PlayerState(Player player) {
        this.player = player;
        this.state = State.IDLE;
    }

    public State getState() {
        return state;
    }

    public void update() {
        switch(state) {
            case IDLE:
                if(player.velocityX != 0 || player.velocityY != 0)
                    state = State.WALK;
                break;
            case WALK:
                if(player.velocityX == 0 && player.velocityY == 0)
                    state = State.IDLE;
                break;
            default:
                break;
        }
    }
}
