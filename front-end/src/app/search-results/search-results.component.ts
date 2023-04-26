import { Component } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import IMovie from 'src/app/models/movie.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent {
  searchQuery = this.route.snapshot.params['search']

  constructor(private movieService: MovieService,
              private route: ActivatedRoute) {}
}
