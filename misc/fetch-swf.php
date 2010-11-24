<?php

$lines = file('arcades2.csv');
$count = 0 ;
foreach($lines as $line ) {
    //explode on comma
    $pieces = explode(",",$line);
    $address = trim($pieces[2]);
    echo "fetch => $pieces[1] URL=>$address \n";
    //fetch content
    $swfBlob = file_get_contents($address);
    //write this blob back into a file

    $name = trim($pieces[1]);
    //strip non alphanumeric
    $name = preg_replace("[^a-zA-Z0-9]",' ',$name);
    //squeeze extra spaces
    $ofile = preg_replace('/\s\s+/', ' ', $name);
    //replace single space by dashes
    $ofile = preg_replace('/\s/', '-', $ofile);
    $ofile = strtolower($ofile);
			
    //output file name
    $ofile = "./games/".$ofile.".swf";
    $count++ ;

    //do nothing if file exists
    if (file_exists($ofile)) {
        echo "$count :  $ofile already exists \n \n";
        continue ;
    }
    //file pointer
    $fp = fopen($ofile, 'w') or die("can't open file ".$ofile);
    fwrite($fp, $swfBlob);
    fclose($fp);
    //echo "$count :  wrote $ofile \n \n";
    sleep(15);
}


?>

