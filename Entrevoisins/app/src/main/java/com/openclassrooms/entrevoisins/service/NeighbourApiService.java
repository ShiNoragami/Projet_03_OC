package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get all favorite neighbour
     */
    //Recupere la liste des voisins
    List<Neighbour> getFavoriteNeighbour();

    //Verifie si oui ou non favoris
    Boolean isfavorite(Neighbour neighbour);

    /**
     * Add Favorite Neighbour
     */
    void addfavorite(Neighbour neighbour);

    /**
     * Remove favorite Neighbour
     */
    void removefavorite(Neighbour neighbour);


}
