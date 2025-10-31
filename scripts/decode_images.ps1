# Decode all .jpg.b64 files in the images directory into .jpg files

$imagesDir = Join-Path $PSScriptRoot "..\\images"
Get-ChildItem -Path $imagesDir -Filter "*.b64" | ForEach-Object {
    $b64Path = $_.FullName
    # remove only the trailing .b64 so files like name.jpg.b64 -> name.jpg
    $outFile = $b64Path -replace '\\.b64$',''
    Write-Host "Decoding $b64Path -> $outFile"
    $b64 = Get-Content -Raw -Path $b64Path
    $bytes = [System.Convert]::FromBase64String($b64)
    [System.IO.File]::WriteAllBytes($outFile, $bytes)
}
# Cleanup accidental duplicate names created earlier (e.g. name.jpg.jpg or name.jpg..jpg)
Get-ChildItem -Path $imagesDir -Filter "*.jpg.jpg" -File -ErrorAction SilentlyContinue | ForEach-Object {
    Write-Host "Removing duplicate file: $($_.FullName)"
    Remove-Item $_.FullName -Force
}
Get-ChildItem -Path $imagesDir -Filter "*..jpg" -File -ErrorAction SilentlyContinue | ForEach-Object {
    Write-Host "Removing malformed file: $($_.FullName)"
    Remove-Item $_.FullName -Force
}
Write-Host "Decoding complete. Generated files in: $imagesDir"