package cn.cjun.blog.rsa;

/**
 * rsa加解密用的公钥和私钥
 *
 * @author Administrator
 */
public class RsaKeys {

    //生成秘钥对的方法可以参考这篇帖子
    //https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
/*	private static final String serverPrvKeyPkcs8 = "MIIEogIBAAKCAQEAr7ZxaLI93hX2/09HGBBE4eOv/witnweF3GsrIoMDRRdUYGT0\n" +
		"pp37mp06aOjRIhThEG6Zu982XNsyppbHJldNci+TmDLO71Ja0n6FXMebYGlMpgKn\n" +
		"tH2pqEY3w7HM3yoKDKQK6L3L8bC85hMK6f/OOLbj6Kx53LhD5K84RUBrJofrpOGw\n" +
		"7GZwp+7dKvwng5DjWaTVQpI4erXuimiQSPVefKf7Ub5kElu2bKRTmq41E35S/NoF\n" +
		"u6aRPWReuoYudW4LwIFdj7ap7oL8nz+L87PlXKIXnZtT8qQPWnAbuxKQQ7P6Xw2m\n" +
		"Fjq0BOWygJeGfNPL537pnPyADyeCHqNpBkCD9wIDAQABAoIBACO4eCerjI44cuUG\n" +
		"LdLDg+1UBzRWhE0/D0R2+ObkMWb7TijmfSHeHgcZjYVERzehfPuFMHWT9A4+dXaO\n" +
		"UmAGAXWEHIIlWhIm8NW36M6quXiJD470HTnBDhtqYu3CmH6Jok9djeYwp2Qz5ML0\n" +
		"/hQB799xtrVXUtK/FvZHdK4HYYEMbGjdmRdc8eN9X1Z7LizX8+FzbzbrR/sGvM6x\n" +
		"qRi2WE2V0gClN27rsMCEYXNHIuRNaL1a86d9K+daO+767qc3lYZb+HXVjicpbSQ+\n" +
		"6LDhYHvOom8P97/dyH0JVx/vf0MhCG7yz8arIBFg2+94EH23spverijEd4LFi2V0\n" +
		"X39MdPECgYEA1/7YbwwzLw+/FSGcLtQp8WQCwVJfq2Q6BkyWsjXFLwiNXXQaul2/\n" +
		"EUknabV4Ij/TkeWXKOyPFj7WyyCU8PgPTzA3SLY45PdBaYCrK99ouU6kPXszeZ/d\n" +
		"AHXGzfD8zOCjTSb6931kuYf49A3S15T2JoheMv/rfk04L5kYXeyQNVkCgYEA0EGj\n" +
		"YXwqs2Vbtb8yCQRx3VCg+Ef//6kKmaTv54WCIvcW6oY1FGc2QVUeEc+t5PPTs0yR\n" +
		"uGNlRiSxI0lC99oij3yQvPH4g63YGSs41jdqyvVRQmGJwGLtU9ILn5T2jfA2SeDe\n" +
		"/88jl6zi9SCpvqWb9pUAM4BxhJZQ5HCGx1EHSc8CgYBQMWIR+KmYM3RyWAQwrrzT\n" +
		"zpnnRLoYXCHe7NiWWbhX+gjiRZUW1G93730eli2AKcpXRLKWkx2c2L/Oss4ZmT+1\n" +
		"2uTrrhv5hoNWtFrY8x1ij3wTB4bZBS18Wj3xgCObvVtj7bEH6hpXd9BKbEd3muq4\n" +
		"67Btpu67+dd+dIzDcm0oIQKBgFH8BR2zi/vjwx7hjgkOvuSpgTU84wL6wTmLPQag\n" +
		"AINTUqow+2R03V8K6zOH1qVzd7v2QMeeGZhriXCivsJkFYb3OuOnPnZYUeJWqaFr\n" +
		"X5dWzA6d52TdjSCaaAkC0609C6I57JA4M4LahN6LZUewWp3QOqwLvKS9qErGIckF\n" +
		"lSDxAoGAMRZzcSccF3Vds7tmf8DNAEMu4AbQdkiSpiWrkLwUAQEpOx/+zjtbJh7k\n" +
		"66yvXiOwk6wfvy8KtSCagwVcdNL2ouoJwPaVSOdgNn1yIdJDLINri0CFx3eZ9xXJ\n" +
		"757KoZcivBHI7/bszEyTJqaouN1msFFSUaS7hRi6XEt0pAJJ2TY=";*/

    //服务器公钥
    private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw3N5NGw7I5Ze1OgtqsQH\n" +
			"54Ic+E9Rd1pEd5GA2g0MJAn79B8YjPlyCSHVgrclzIa+7+5uMnb+U6ZnV/Qw0SUv\n" +
			"BilKqevlI6h6hPgftnQ1SfdEweTVIoQxJGboiG+H8K11hiRZuIdNoHdlD/3mIlpN\n" +
			"McbA1JWItSkWIBvmwlC+I5MGHdGrgOblpQqBbYiW9o+fwm2LH2W9VSggq/il5V9E\n" +
			"k7o2WG+SOyfluEt4yGpOY0i+EfRoiBsVa+yU2OcIoOPHhxSvCmrBhYfSPMayFG3v\n" +
			"htrZj++lDvy4cYM/iK6x+b+v4MRFvWVV3w/1zGYcPJZJI/yt01lEh9q3hmRQkM72\n" +
			"VQIDAQAB";

    //服务器私钥(经过pkcs8格式处理)
    private static final String serverPrvKeyPkcs8 = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDDc3k0bDsjll7U\n" +
			"6C2qxAfnghz4T1F3WkR3kYDaDQwkCfv0HxiM+XIJIdWCtyXMhr7v7m4ydv5TpmdX\n" +
			"9DDRJS8GKUqp6+UjqHqE+B+2dDVJ90TB5NUihDEkZuiIb4fwrXWGJFm4h02gd2UP\n" +
			"/eYiWk0xxsDUlYi1KRYgG+bCUL4jkwYd0auA5uWlCoFtiJb2j5/CbYsfZb1VKCCr\n" +
			"+KXlX0STujZYb5I7J+W4S3jIak5jSL4R9GiIGxVr7JTY5wig48eHFK8KasGFh9I8\n" +
			"xrIUbe+G2tmP76UO/Lhxgz+IrrH5v6/gxEW9ZVXfD/XMZhw8lkkj/K3TWUSH2reG\n" +
			"ZFCQzvZVAgMBAAECggEAaM9bh8kiTYstf26iTpigxHz5nA6s2RwG6zeTqVql0A2q\n" +
			"lta2C6MYi90g6d/c3TjE21U4sscsyx7I/FludDlEZkFuUIC3d8+5I+UK3ISkru1y\n" +
			"aaeUw9WvpJ2qxL9KXr4l77E+i73C2vIrt8+nwXQ7QR4b+ePWU4+c1csgi8NQszIL\n" +
			"026kzql5HI+1FauLj+ygBd6JfV7SDgi8SCY+X0aUlJ2/30dFruBrCWiZrVCKE3FC\n" +
			"eoVAsdYh9SNjZP5Li5KDmd4W1/Ss6xt7SA1mzdulmt7NQWDOX5f6/zkn0a1oWRtf\n" +
			"IPaj44jp1DO4J2dUlPXMZRq90NsOS+YVteYCWWeXYQKBgQDxZZoZch36COPUC5Io\n" +
			"ThoVKQfE6Cbg4BxDZCRfi5cQkKh3wKR2MR0x9R1fy4vcPHh2TeK9lT2rCnKtucYm\n" +
			"ZK5fcZNs2iikATav+fn3QzQFLZrPJeTH9xRHgWWJTXSjDLDfim3OF7MnJKLmPnUh\n" +
			"+C7KcaQF0dCmYkuDCx6ua/I5owKBgQDPRlSl1RsH4y6IR+jJe5YeA/399MCGszR3\n" +
			"AJltpVP5xb9esdktAraKMwppPzo7k+EUG2yrThK1h/TqnYN69YknMUogK6ECTeK3\n" +
			"IGmr4BGBhgf6y5uteu2qwIinDzEC0NNuK98zzMNkiWBxFVEZ/t2Mmmbans9tb9PX\n" +
			"Npj20jb/pwKBgQDUjWv0v+DZSn6hNVcKmkqpGym2Hdwt4efBBypn3tezREyytTOa\n" +
			"xoA4fuXQdb++upgvQ0FFQWW9d0Om6I8rOze23qC7FhT25AKtTOTm7Yuei5tmjq01\n" +
			"zb9TfwJStZrf/sq26oLpF0EuVDoDljc5I/i/VPTiEu8vshlfV6k6V94/FQKBgQCI\n" +
			"gx6e9aLO6A4ujYXApVeyPxjaWIcwb/h+4/Mo5rlPCLFIUcPiRCYrEQCrXowgzv1v\n" +
			"e7xveoUR8BAbMVB1/B6vpGPYmkN5vAg0afGO1I1TDA5XXCAU+qBrVFDsDjJOU3Dc\n" +
			"36IqgJKztVLCR7LZdyU9RDe3ht7AieUwmfHWpY65gwKBgQDJuTXx2NZenxTfVCKt\n" +
			"USh/5qh9RWkqL3e+2rx9b4BvXP/Aftbnt5plhrD9tqURFBRDK1qj6JMRCOI32scy\n" +
			"jdNTNFh/FbC1ncI0Cgjuorz2o7SbNfUBxO2F/A1JbwRDmkXhOT2XYYKCFWqfhiIQ\n" +
			"5bLSwOqWHFREgFCX3Y1nGDtx9g==";


    public static String getServerPubKey() {
        return serverPubKey;
    }

    public static String getServerPrvKeyPkcs8() {
        return serverPrvKeyPkcs8;
    }

}
