package com.android.daniel.challengeteam.model;


public class Player {

    private String idPlayer;
    private String idUser;
    private String name;
    private String psnUsername;
    private String xboxUsername;
    private String Country;
    private int boughtCoins;
    private int coins;
    private int earnedCoins;
    private int lostCoins;
    private int taxedCoins;
    private int victories;
    private int loss;
    private int draws;
    private int gamesPlayed;

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public Player() {
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getBoughtCoins() {
        return boughtCoins;
    }

    public void setBoughtCoins(int boughtCoins) {
        this.boughtCoins = boughtCoins;
    }

    public int getEarnedCoins() {
        return earnedCoins;
    }

    public void setEarnedCoins(int earnedCoins) {
        this.earnedCoins = earnedCoins;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getLostCoins() {
        return lostCoins;
    }

    public void setLostCoins(int lostCoins) {
        this.lostCoins = lostCoins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsnUsername() {
        return psnUsername;
    }

    public void setPsnUsername(String psnUsername) {
        this.psnUsername = psnUsername;
    }

    public int getTaxedCoins() {
        return taxedCoins;
    }

    public void setTaxedCoins(int taxedCoins) {
        this.taxedCoins = taxedCoins;
    }

    public String getXboxUsername() {
        return xboxUsername;
    }

    public void setXboxUsername(String xBoxUsername) {
        this.xboxUsername = xBoxUsername;
    }

}
