import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-share-news',
  templateUrl: './share-news.component.html',
  styleUrls: ['./share-news.component.css']
})
export class ShareNewsComponent implements OnInit {

  newsForm!: FormGroup
  tagsArray: string[] = []

  @ViewChild('photo') photo!: ElementRef

  constructor(
      private fb: FormBuilder,
      private newsSvc: NewsService,
      private router: Router) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm() {
    this.newsForm = this.fb.group({
      title: this.fb.control<string>('', 
          [ Validators.required, Validators.minLength(5) ]),
      description: this.fb.control<string>('', 
          [ Validators.required, Validators.minLength(5) ])
    })
  }

  pushToArray(tags: string) {
    console.log(tags)
    tags.split(' ').forEach(v => this.tagsArray.push(v))
    console.log(this.tagsArray)

  }

  deleteTagFromArray(tagToDelete: string) {
    this.tagsArray = this.tagsArray.filter(
      (tag) => {
        return tag !== tagToDelete;
      }
    )
  }

  postNews() {
    console.log(this.photo)
    const formData = new FormData
    formData.set('title', this.newsForm.get('title')?.value)
    formData.set('photo', this.photo.nativeElement.files[0])
    formData.set('description', this.newsForm.get('description')?.value)
    formData.set('tags', JSON.stringify(this.tagsArray))
    console.log(formData.forEach(v => console.log(v)))

    this.newsSvc.postNews(formData)
        .then(
          result => {
            alert(result['newsId'])
            this.router.navigate(['/tags'])
          }
        )
        .catch(
          error => {
            alert(error['error'])
          }
        )
  }

  photoIsAttached(photo: HTMLInputElement) {
    if (this.photo.nativeElement.files.length > 0)
      return true
    return false
  }
}
