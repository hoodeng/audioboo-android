/**
 * This file is part of AudioBoo, an android program for audio blogging.
 * Copyright (C) 2009 BestBefore Media Ltd. All rights reserved.
 *
 * Author: Jens Finkhaeuser <jens@finkhaeuser.de>
 *
 * $Id$
 **/

package fm.audioboo.jni;


/**
 * This is *not* a full JNI wrapper for the FLAC codec, but merely exports
 * the minimum of functions necessary for the purposes of the AudioBoo client.
 **/
public class FLACStreamDecoder
{
  /***************************************************************************
   * Interface
   **/

  /**
   * Initialize the stream decoder with the file given by infile.
   * TODO: Will possibly need a different constructor that accepts URLs.
   **/
  public FLACStreamDecoder(String infile)
  {
    init(infile);
  }



  public void release()
  {
    deinit();
  }



  public void reset(String infile)
  {
    deinit();
    init(infile);
  }



  protected void finalize() throws Throwable
  {
    try {
      deinit();
    } finally {
      super.finalize();
    }
  }



  /***************************************************************************
   * JNI Implementation
   **/

  // Pointer to opaque data in C
  private long  mObject;

  /**
   * Constructor equivalent
   **/
  native private void init(String infile);

  /**
   * Destructor equivalent, but can be called multiple times.
   **/
  native private void deinit();

  /**
   * Returns the bits per sample in the infile, or -1 if that is unknown.
   **/
  native public int bitsPerSample();

  /**
   * Returns the number of channels in the infile, or -1 if that is unknown.
   **/
  native public int channels();

  /**
   * Returns the sample rate in the infile, or -1 if that is unknown.
   **/
  native public int sampleRate();

  /**
   * Returns the minimum buffer size you need to hand to read() below, or -1
   * if it's unknown.
   **/
  native public int minBufferSize();

  /**
   * Reads data from the decoder, and writes it into the provided buffer. The
   * buffer must be at least minBufferSize() in size.
   * Returns the number of bytes actually read, or -1 on fatal errors.
   **/
  native public int read(byte[] buffer, int bufsize);

  // Load native library
  static {
    System.loadLibrary("audioboo-native");
  }
}
