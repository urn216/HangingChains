package code.models;

import code.core.Bridge;
import code.math.Vector2;
import code.math.Vector3;

public class CubeBridge extends Model {

  public CubeBridge(Bridge b) {
    super(generateMesh(b));
  }

  private static Object[][] generateMesh(Bridge b) {
    Object[][] res = new Object[3][];

    Vector3[] verts = generateVerts(b);
    res[0] = verts;
    res[1] = generateFaces(verts);
    res[2] = new Vector2[]{};

    return res;
  }

  private static Vector3[] generateVerts(Bridge b) {
    Vector3[] res = new Vector3[(b.getLength()*b.getWidth()+(b.getLength()-1)*(b.getWidth()-1))*8];

    double lOff = b.getLength()/2.0;
    double wOff = b.getWidth ()/2.0;
    int index = 0;

    for (int i = 0; i < b.getLength(); i++) {
      double x = i-lOff;
      double y = (int)(b.getHeights()[i]*2)/2.0;
      double y2 = (i < b.getLength() - 1) ? (int)(b.getHeights()[i]+b.getHeights()[i+1])/2.0 : y;
      double z = i-lOff;
      for (int j = 0; j < b.getWidth(); j++) {
        boundingBox(res, index, x+j-wOff, y, z-j+wOff);
        index+=8;
        if (i < b.getLength() - 1 && j < b.getWidth() - 1) {
          boundingBox(res, index, x+j-wOff+1, y2, z-j+wOff);
          index+=8;
        }
      }
    }
    return res;
  }

  private static void boundingBox(Vector3[] dest, int offset, double x, double y, double z) {
    dest[offset+0] = new Vector3(x-0.5, y-0.25, z-0.5);
    dest[offset+1] = new Vector3(x-0.5, y-0.25, z+0.5);
    dest[offset+2] = new Vector3(x+0.5, y-0.25, z-0.5);
    dest[offset+3] = new Vector3(x+0.5, y-0.25, z+0.5);
    dest[offset+4] = new Vector3(x-0.5, y+0.25, z-0.5);
    dest[offset+5] = new Vector3(x-0.5, y+0.25, z+0.5);
    dest[offset+6] = new Vector3(x+0.5, y+0.25, z-0.5);
    dest[offset+7] = new Vector3(x+0.5, y+0.25, z+0.5);
  }

  private static Tri[] generateFaces(Vector3[] verts) {
    Tri[] res = new Tri[(int)(verts.length*1.5)];
    for (int i = 0; i < res.length/12; i++) {
      cubeFaces(res, verts, i);
    }
    return res;
  }

  private static void cubeFaces(Tri[] dest, Vector3[] verts, int offset) {
    int fOff = offset*12;
    int vOff = offset*8;
    dest[fOff+0 ] = new Tri(new Vector3[]{verts[vOff+0], verts[vOff+3], verts[vOff+2]}, new int[]{vOff+0+1, vOff+3+1, vOff+2+1});
    dest[fOff+1 ] = new Tri(new Vector3[]{verts[vOff+0], verts[vOff+1], verts[vOff+3]}, new int[]{vOff+0+1, vOff+1+1, vOff+3+1});
    dest[fOff+2 ] = new Tri(new Vector3[]{verts[vOff+4], verts[vOff+7], verts[vOff+6]}, new int[]{vOff+4+1, vOff+7+1, vOff+6+1});
    dest[fOff+3 ] = new Tri(new Vector3[]{verts[vOff+4], verts[vOff+5], verts[vOff+7]}, new int[]{vOff+4+1, vOff+5+1, vOff+7+1});
    dest[fOff+4 ] = new Tri(new Vector3[]{verts[vOff+0], verts[vOff+6], verts[vOff+2]}, new int[]{vOff+0+1, vOff+6+1, vOff+2+1});
    dest[fOff+5 ] = new Tri(new Vector3[]{verts[vOff+0], verts[vOff+4], verts[vOff+6]}, new int[]{vOff+0+1, vOff+4+1, vOff+6+1});
    dest[fOff+6 ] = new Tri(new Vector3[]{verts[vOff+1], verts[vOff+7], verts[vOff+3]}, new int[]{vOff+1+1, vOff+7+1, vOff+3+1});
    dest[fOff+7 ] = new Tri(new Vector3[]{verts[vOff+1], verts[vOff+5], verts[vOff+7]}, new int[]{vOff+1+1, vOff+5+1, vOff+7+1});
    dest[fOff+8 ] = new Tri(new Vector3[]{verts[vOff+0], verts[vOff+5], verts[vOff+1]}, new int[]{vOff+0+1, vOff+5+1, vOff+1+1});
    dest[fOff+9 ] = new Tri(new Vector3[]{verts[vOff+0], verts[vOff+4], verts[vOff+5]}, new int[]{vOff+0+1, vOff+4+1, vOff+5+1});
    dest[fOff+10] = new Tri(new Vector3[]{verts[vOff+2], verts[vOff+7], verts[vOff+3]}, new int[]{vOff+2+1, vOff+7+1, vOff+3+1});
    dest[fOff+11] = new Tri(new Vector3[]{verts[vOff+2], verts[vOff+6], verts[vOff+7]}, new int[]{vOff+2+1, vOff+6+1, vOff+7+1});
  }
}
