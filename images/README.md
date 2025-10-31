# Product Images Directory

This directory contains product images for the toy store application.

## Placeholder images included

The repository includes small placeholder images encoded in base64 (`*.jpg.b64`).
Run the decode script to generate usable image files in this directory.

### How to decode (Windows PowerShell / pwsh)

From the project root run:

```powershell
scripts\decode_images.ps1
```

This will decode the included base64 files and produce the following files in `images/`:

- `lego_classic.jpg`
- `barbie_dreamhouse.jpg`
- `hotwheels_track.jpg`
- `nerf_blaster.jpg`
- `wooden_puzzle.jpg`
- `rc_car.jpg`
- `art_kit.jpg`
- `board_games.jpg`

These placeholders are minimal images so the UI can display something. Replace them with real product images (JPG/PNG) with recommended size ~400x300 for best display.

## Fallback Behavior

If an image file is still missing or cannot be loaded, the application will display a toy emoji (🪀) as a placeholder.
