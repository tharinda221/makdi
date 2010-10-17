
<div class="widget" style="border:5px solid #ccc;">
        <h2> $book.title$ </h2>
            <div class="photo">
                <img src="$book.imageLink$"  title="$book.title$"  class="alignleft" alt="$book.title$"/>
                <div class="regular">
                    $book.description$
                </div>
                
                $if(book.isEmbeddable)$
                  <div class="book-review">
                       <script type="text/javascript" src="http://books.google.com/books/previewlib.js"></script>
                        <script type="text/javascript">
                            GBS_insertPreviewButtonPopup('$book.bookId$');
                        </script>
                    </div>
                $endif$
            </div> <!-- photo -->
           
            <div class="download">
                <div> <a href="$book.link$"> Go to book details &gt;</a></div>
            </div>
            <br style="clear:both"/>
                
</div>







