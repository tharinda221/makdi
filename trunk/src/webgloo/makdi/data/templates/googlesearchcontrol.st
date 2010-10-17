
<div class="widget">

    <script src="http://www.google.com/jsapi"  type="text/javascript"></script>
    <script language="Javascript" type="text/javascript">
    //<![CDATA[
    google.load('search', '1');

    function OnLoad() {
      // Create a search control
      var searchControl = new google.search.SearchControl();
      // Add in a full set of searchers
      var options = new google.search.SearcherOptions();
      options.setExpandMode(google.search.SearchControl.EXPAND_MODE_OPEN);
      searchControl.addSearcher(new google.search.BlogSearch(),options);
      // tell the searcher to draw itself and tell it where to attach
      searchControl.draw(document.getElementById("GoogleSearchControl"));
      // execute an inital search
      searchControl.execute("$keyword$");
    }
    google.setOnLoadCallback(OnLoad);

    //]]>
    </script>

    <div id="GoogleSearchControl">Loading ....</div>


</div>
