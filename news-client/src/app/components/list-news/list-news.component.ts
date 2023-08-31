import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { News } from 'src/app/news';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-list-news',
  templateUrl: './list-news.component.html',
  styleUrls: ['./list-news.component.css']
})
export class ListNewsComponent implements OnInit {

  newsList: News[] = []

  constructor(
      private activatedRoute: ActivatedRoute,
      private newsSvc: NewsService) {}

    ngOnInit(): void {
      const tag = this.activatedRoute.snapshot.params['tag']
      this.newsSvc.getNewsByTag(tag)
          .then(
            result => {
              this.newsList = result
            }
          )
    }
}
