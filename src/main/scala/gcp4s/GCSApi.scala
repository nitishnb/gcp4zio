package gcp4s

import com.google.api.gax.paging.Page
import com.google.cloud.storage.Blob
import com.google.cloud.storage.Storage.BlobListOption
import zio.ZIO

object GCSApi {
  trait Service[F[_]] {
    def putObject(bucket: String, prefix: String, file: String): F[Blob]
    def lookupObject(bucket: String, prefix: String, key: String): F[Boolean]
    def listObjects(bucket: String, options: List[BlobListOption]): F[Page[Blob]]
    def listObjects(bucket: String, prefix: String): F[List[Blob]]
    def copyObjectsGCStoGCS(
        src_bucket: String,
        src_prefix: String,
        target_bucket: String,
        target_prefix: String,
        parallelism: Int,
        overwrite: Boolean
    ): F[Unit]
    def copyObjectsLOCALtoGCS(
        src_path: String,
        target_bucket: String,
        target_prefix: String,
        parallelism: Int,
        overwrite: Boolean
    ): F[Unit]
  }

  def putObject(bucket: String, prefix: String, file: String): ZIO[GCSEnv, Throwable, Blob] =
    ZIO.accessM(_.get.putObject(bucket, prefix, file))
  def lookupObject(bucket: String, prefix: String, key: String): ZIO[GCSEnv, Throwable, Boolean] =
    ZIO.accessM(_.get.lookupObject(bucket, prefix, key))
  def listObjects(bucket: String, options: List[BlobListOption]): ZIO[GCSEnv, Throwable, Page[Blob]] =
    ZIO.accessM(_.get.listObjects(bucket, options))
  def listObjects(bucket: String, prefix: String): ZIO[GCSEnv, Throwable, List[Blob]] =
    ZIO.accessM(_.get.listObjects(bucket, prefix))
  def copyObjectsGCStoGCS(
      src_bucket: String,
      src_prefix: String,
      target_bucket: String,
      target_prefix: String,
      parallelism: Int,
      overwrite: Boolean
  ): ZIO[GCSEnv, Throwable, Unit] =
    ZIO.accessM(_.get.copyObjectsGCStoGCS(src_bucket, src_prefix, target_bucket, target_prefix, parallelism, overwrite))
  def copyObjectsLOCALtoGCS(
      src_path: String,
      target_bucket: String,
      target_prefix: String,
      parallelism: Int,
      overwrite: Boolean
  ): ZIO[GCSEnv, Throwable, Unit] =
    ZIO.accessM(_.get.copyObjectsLOCALtoGCS(src_path, target_bucket, target_prefix, parallelism, overwrite))
}