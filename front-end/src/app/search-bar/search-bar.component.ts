import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';
import IMovie from 'src/app/models/movie.model';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  search = {
    searchQuery: ''
  }

  constructor(private router: Router) {}

  getSearchResults() {
    this.router.navigate(['search'], {queryParams: {query: this.search.searchQuery, page: '1'}})
  }
}
