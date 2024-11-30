for filename in ./*; do mv "./$filename" "./$(echo "$filename" | sed -e 's/frame_//g')";  done
rename 's/_delay-[0-9]+\.[0-9]+s//' *.png
rename 's/^0//' *.png