import { Component } from '@angular/core';
import { MovieService } from '../services/movie.service';
import { Input } from '@angular/core';
import IMovie from '../models/movie.model';
import { environment } from 'src/environments/environment';
import { ActivatedRoute, NavigationEnd } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.css'],
  providers: [DatePipe]
})
export class MoviesListComponent {
  movies: IMovie[] = []
  crtPage = 1
  lastPage = 1
  searchQuery = ''
  comingFrom = ''

  constructor(public movieService: MovieService,
    public route: ActivatedRoute,
    private router: Router) {
      router.events.subscribe((val) => {
        if(val instanceof NavigationEnd) {
          this.route.queryParams.subscribe(params => {
            console.log(params)
            this.crtPage = +(params['page'])
            this.searchQuery = params['query']
          })
          this.comingFrom = val.url.substring(
            val.url.indexOf("/") + 1, 
            val.url.lastIndexOf("?"))
          if (!this.crtPage) {
            this.crtPage = 1
          }
          this.loadMoviePage()
        }
      })
    }

  ngOnInit(): void {
    if (!this.crtPage) {
      this.crtPage = 1
    }
 //   this.loadMoviePage()
  }

  loadMoviePage(): void {
    console.log("from " + this.comingFrom)
    if (this.comingFrom === 'search') {
      this.movieService.searchMovie(this.searchQuery, this.crtPage.toString()).subscribe(result => {
        this.lastPage = result.total_pages
        this.movies = result.results
        this.movies.forEach((element: any) => {
          if (!element.backdrop_path) {
            element.backdrop_path = environment.generic_wallpaper
          }
          else element.backdrop_path = `${environment.api_image_url}/${element.backdrop_path}`
          this.movieService.getMovieScore(element.id).subscribe(result => {
            element.user_score = result
          })
          element.vote_average /= 2
        })
      })
    }
    else {
      this.movieService.getMovies(this.crtPage.toString()).subscribe(result => {
        this.movies = result.results
        this.movies.forEach((element: any) => {
          if (!element.backdrop_path) {
            element.backdrop_path = environment.generic_wallpaper
          }
          else element.backdrop_path = `${environment.api_image_url}/${element.backdrop_path}`
          this.movieService.getMovieScore(element.id).subscribe(result => {
            element.user_score = result
          })
          element.vote_average /= 2
        })
      })
    }
  }

}
