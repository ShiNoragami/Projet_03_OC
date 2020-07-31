package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }


    /**
     * Ajout des fonctions pour la liste favoris
     */

    //Ajoute un favoris
    @Override
    public void addfavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(true);
    }

    //Retourne si oui ou non il est favoris
    @Override
    public Boolean isfavorite(Neighbour neighbour) {
        return neighbours.get(neighbours.indexOf(neighbour)).getFavorite();
    }

    //Créer une liste de favoris
    @Override
    public List<Neighbour> getFavoriteNeighbour() {
        List<Neighbour> favoriteNeighbourList = new ArrayList<>();
        for (Neighbour n : neighbours) {
            if (n.getFavorite()) {
                favoriteNeighbourList.add(n);
            }
        }
        return favoriteNeighbourList;
    }

    //Supprime un Favoris
    @Override
    public void removefavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(false);
    }
}
