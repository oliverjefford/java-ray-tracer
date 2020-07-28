A CPU ray tracer created during studies at Swansea University as part of the dissertation module for a BSc (Hons).
Author - Oliver Jefford

This program renders any constructed scene to an image using Phong shading and recursive ray tracing.

Stanford repository models can be rendered using the .txt files provided. This program does not currently 
read standard .ply file formats. Each point list file is respectively matched with a shape file to 
construct a complex model. 

This program has been uploaded with the intended purpose of allowing others to understand the functionality 
of a ray tracer and potentially develop sections further. 

Ray Tracer/
    complex models/
        contains each .txt file (points and shapes) to construct a complext model
    src/ 
        contains each .java file for compiliing the program
    result - the output for the rendered scene

For each .txt model points file:
    Each line contains a point within the scene (x,y,z).
    The program will run through each line and construct a point object to store into an array.
For each .txt model shapes file:
    Each line contains the indexed value of the points list to construct each object in the complex model.
    The program will use the indexed values within the file to construct each shape object and will be 
    subsequently stored into an array.
