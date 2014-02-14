/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

package name.abhijitsarkar.moviemanager.web

import javax.validation.constraints.NotNull
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.facade.MovieFacade

import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.validation.constraints.NotNull

/**
 * @author Abhijit Sarkar
 */
@Path('movies')
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
class MovieResource {
    @Inject
    private MovieFacade movieFacade

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    String index(
            @NotNull(message = 'Movie directory cannot be null.')
            @FormParam('dir') String movieDirectory) {
        movieFacade.index(movieDirectory)
    }

    @GET
    Set<MovieRip> fetchAll() {
        movieFacade.fetchAll()
    }

    @GET
    @Path('{searchText}')
    Set<MovieRip> advancedSearch(@PathParam('searchText') String searchText) {
        movieFacade.advancedSearch(searchText)
    }

    @GET
    @Path('{searchField}/{searchText}')
    Set<MovieRip> searchByField(@PathParam('searchField') String indexField,
                                @PathParam('searchText') String searchText) {
        movieFacade.searchByField(searchText, indexField)
    }
}
