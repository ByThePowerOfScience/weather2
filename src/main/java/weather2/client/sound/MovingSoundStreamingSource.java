package weather2.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import weather2.weathersystem.storm.StormObject;
import CoroUtil.util.Vec3;

public class MovingSoundStreamingSource extends MovingSound {

	private StormObject storm = null;
	public float cutOffRange = 128;
	public Vec3 realSource = null;

	//constructor for non moving sounds
    public MovingSoundStreamingSource(Vec3 parPos, ResourceLocation parRes, float parVolume, float parPitch, float parCutOffRange)
    {
        super(parRes);
        this.repeat = false;
        this.volume = parVolume;
        this.pitch = parPitch;
        cutOffRange = parCutOffRange;
        realSource = parPos;
        
        //sync position
        update();
    }
    
    //constructor for moving sounds
    public MovingSoundStreamingSource(StormObject parStorm, ResourceLocation parRes, float parVolume, float parPitch, float parCutOffRange)
    {
        super(parRes);
        this.storm = parStorm;
        this.repeat = false;
        this.volume = parVolume;
        this.pitch = parPitch;
        cutOffRange = parCutOffRange;
        
        //sync position
        update();
    }

    public void update()
    {
    	EntityPlayer entP = FMLClientHandler.instance().getClient().thePlayer;
    	
    	if (entP != null) {
    		this.xPosF = (float) entP.posX;
    		this.yPosF = (float) entP.posY;
    		this.zPosF = (float) entP.posZ;
    	}
    	
    	if (storm != null) {
    		realSource = new Vec3(this.storm.posGround.xCoord, this.storm.posGround.yCoord, this.storm.posGround.zCoord);
    	}
    	
    	float var3 = (float)((cutOffRange - (double)MathHelper.sqrt_double(getDistanceFrom(realSource, new Vec3(entP.posX, entP.posY, entP.posZ)))) / cutOffRange);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }
        
        volume = var3;
    }
    
    public double getDistanceFrom(Vec3 source, Vec3 targ)
    {
        double d3 = (double)source.xCoord - targ.xCoord;
        double d4 = (double)source.yCoord - targ.yCoord;
        double d5 = (double)source.zCoord - targ.zCoord;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

}
