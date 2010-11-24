<?php

$lines = file('arcades3.csv');
$count = 0 ;

foreach($lines as $line ) {

	$count++ ;
    //explode on comma
    $pieces = explode(",",$line);

    $token = trim($pieces[1]);
    //strip non alphanumeric
    $seoKey = preg_replace("[^a-zA-Z0-9]",' ',$token);
    //squeeze extra spaces
    $seoKey = preg_replace('/\s\s+/', ' ', $seoKey);
    //replace single space by dashes
    $seoKey = preg_replace('/\s/', '-', $seoKey);
	$seoKey = strtolower($seoKey);

	$SQL = "insert ignore into gloo_auto_keyword (org_id, token, seo_key, created_on) " ;
	$SQL .= " values ('{orgId}' , '{token}' , '{seoKey}', '{createdOn}' ) ; \n " ;

	$orgId = 1175 ;
	$createdOn =  date('Y-m-d G:i:s', time() + $count );

	$SQL = str_replace ( array("{orgId}", "{token}", "{seoKey}", "{createdOn}" ),
						 array($orgId, $token, $seoKey, $createdOn ), $SQL);
	echo $SQL ;

	//if($count == 5 ) break ;
	
}


?>

