package androidx.core.location;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.os.Build;
import androidx.core.util.Preconditions;
import java.util.Iterator;

class GpsStatusWrapper extends GnssStatusCompat {
    private static final int BEIDOU_PRN_COUNT = 35;
    private static final int BEIDOU_PRN_OFFSET = 200;
    private static final int GLONASS_PRN_COUNT = 24;
    private static final int GLONASS_PRN_OFFSET = 64;
    private static final int GPS_PRN_COUNT = 32;
    private static final int GPS_PRN_OFFSET = 0;
    private static final int QZSS_SVID_MAX = 200;
    private static final int QZSS_SVID_MIN = 193;
    private static final int SBAS_PRN_MAX = 64;
    private static final int SBAS_PRN_MIN = 33;
    private static final int SBAS_PRN_OFFSET = -87;
    private Iterator<GpsSatellite> mCachedIterator;
    private int mCachedIteratorPosition;
    private GpsSatellite mCachedSatellite;
    private int mCachedSatelliteCount = -1;
    private final GpsStatus mWrapped;

    GpsStatusWrapper(GpsStatus gpsStatus) {
        GpsStatus gpsStatus2 = (GpsStatus) Preconditions.checkNotNull(gpsStatus);
        this.mWrapped = gpsStatus2;
        this.mCachedIterator = gpsStatus2.getSatellites().iterator();
        this.mCachedIteratorPosition = -1;
        this.mCachedSatellite = null;
    }

    public int getSatelliteCount() {
        int i;
        synchronized (this.mWrapped) {
            if (this.mCachedSatelliteCount == -1) {
                for (GpsSatellite next : this.mWrapped.getSatellites()) {
                    this.mCachedSatelliteCount++;
                }
                this.mCachedSatelliteCount++;
            }
            i = this.mCachedSatelliteCount;
        }
        return i;
    }

    public int getConstellationType(int satelliteIndex) {
        if (Build.VERSION.SDK_INT < 24) {
            return 1;
        }
        return getConstellationFromPrn(getSatellite(satelliteIndex).getPrn());
    }

    public int getSvid(int satelliteIndex) {
        if (Build.VERSION.SDK_INT < 24) {
            return getSatellite(satelliteIndex).getPrn();
        }
        return getSvidFromPrn(getSatellite(satelliteIndex).getPrn());
    }

    public float getCn0DbHz(int satelliteIndex) {
        return getSatellite(satelliteIndex).getSnr();
    }

    public float getElevationDegrees(int satelliteIndex) {
        return getSatellite(satelliteIndex).getElevation();
    }

    public float getAzimuthDegrees(int satelliteIndex) {
        return getSatellite(satelliteIndex).getAzimuth();
    }

    public boolean hasEphemerisData(int satelliteIndex) {
        return getSatellite(satelliteIndex).hasEphemeris();
    }

    public boolean hasAlmanacData(int satelliteIndex) {
        return getSatellite(satelliteIndex).hasAlmanac();
    }

    public boolean usedInFix(int satelliteIndex) {
        return getSatellite(satelliteIndex).usedInFix();
    }

    public boolean hasCarrierFrequencyHz(int satelliteIndex) {
        return false;
    }

    public float getCarrierFrequencyHz(int satelliteIndex) {
        throw new UnsupportedOperationException();
    }

    public boolean hasBasebandCn0DbHz(int satelliteIndex) {
        return false;
    }

    public float getBasebandCn0DbHz(int satelliteIndex) {
        throw new UnsupportedOperationException();
    }

    private GpsSatellite getSatellite(int satelliteIndex) {
        GpsSatellite satellite;
        synchronized (this.mWrapped) {
            if (satelliteIndex < this.mCachedIteratorPosition) {
                this.mCachedIterator = this.mWrapped.getSatellites().iterator();
                this.mCachedIteratorPosition = -1;
            }
            while (true) {
                int i = this.mCachedIteratorPosition;
                if (i >= satelliteIndex) {
                    break;
                }
                this.mCachedIteratorPosition = i + 1;
                if (!this.mCachedIterator.hasNext()) {
                    this.mCachedSatellite = null;
                    break;
                }
                this.mCachedSatellite = this.mCachedIterator.next();
            }
            satellite = this.mCachedSatellite;
        }
        return (GpsSatellite) Preconditions.checkNotNull(satellite);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GpsStatusWrapper)) {
            return false;
        }
        return this.mWrapped.equals(((GpsStatusWrapper) o).mWrapped);
    }

    public int hashCode() {
        return this.mWrapped.hashCode();
    }

    private static int getConstellationFromPrn(int prn) {
        if (prn > 0 && prn <= 32) {
            return 1;
        }
        if (prn >= 33 && prn <= 64) {
            return 2;
        }
        if (prn > 64 && prn <= 88) {
            return 3;
        }
        if (prn > 200 && prn <= 235) {
            return 5;
        }
        if (prn < QZSS_SVID_MIN || prn > 200) {
            return 0;
        }
        return 4;
    }

    private static int getSvidFromPrn(int prn) {
        int constellationFromPrn = getConstellationFromPrn(prn);
        if (constellationFromPrn == 2) {
            return prn + 87;
        }
        if (constellationFromPrn == 3) {
            return prn - 64;
        }
        if (constellationFromPrn != 5) {
            return prn;
        }
        return prn - 200;
    }
}
