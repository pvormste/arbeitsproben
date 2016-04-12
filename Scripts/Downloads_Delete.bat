SET download_dir1="C:\Users\username\Downloads"
SET temp_dir="F:\TEMP\delete"
SET log_path="E:\Scripts\logs\Downloads_Delete.log"

mkdir %temp_dir%
robocopy %download_dir1% %temp_dir% /xf dump.txt /MINAGE:7 /E /MOVE /R:1 /W:1 /NP /LOG+:%log_path%
rem robocopy %download_dir2% %temp_dir% /xf dump.txt /MINAGE:7 /E /MOVE /R:1 /W:1 /NP /LOG+:%log_path%
rmdir %temp_dir% /s /q